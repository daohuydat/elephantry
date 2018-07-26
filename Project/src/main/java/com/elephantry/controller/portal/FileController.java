package com.elephantry.controller.portal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.elephantry.entity.Customer;
import com.elephantry.entity.Evaluation;
import com.elephantry.entity.Recommendation;
import com.elephantry.entity.UploadedFile;
import com.elephantry.mahout.hadoop.util.HDConfig;
import com.elephantry.mahout.hadoop.util.HDHelper;
import com.elephantry.model.E;
import com.elephantry.service.ConfigurationService;
import com.elephantry.service.CustomerService;
import com.elephantry.service.EvaluationService;
import com.elephantry.service.RecommendationService;
import com.elephantry.service.UploadedFileService;
import com.elephantry.util.InitConstant;
import com.elephantry.util.LanguageResolver;

@Controller
@RequestMapping(value = { "/{lang}/portal/file", "/portal/file" })
public class FileController {
	
	private ConfigurationService configurationService;
	private RecommendationService recommendationService;
	private UploadedFileService uploadedFileService;
	private CustomerService customerService;
	private EvaluationService evaluationService;
	
	@Autowired
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
	
	@Autowired
	public void setEvaluationService(EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}
	
	@Autowired
	public void setRecommendationService(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}

	@Autowired
	public void setUploadedFileService(UploadedFileService uploadedFileService) {
		this.uploadedFileService = uploadedFileService;
	}

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload(@PathVariable("lang") Optional<String> lang, Principal principal, Model model) {
		LanguageResolver.resolve(lang, model);
		Date today = new Date();
		Customer c = customerService.findByEmail(principal.getName());
		if (c == null) {
			return "redirect:/account/signin";
		}
		String modalPackageStatus = "ok";
		if (c.getPackage1().getPackageId() == 2 && c.getExpiredCredit().before(today)) {
			modalPackageStatus = "expiredPackage2";
		}else if(c.getPackage1().getPackageId() == 3 && c.getExpiredCredit().before(today)) {
			modalPackageStatus = "expiredPackage3";
		}else if(c.getPackage1().getPackageId() == 1 && recommendationService.countRecommendationLastMonthOfCustomer(c.getCustomerId()) >= 1){
			modalPackageStatus = "overPackage1";
		}else if(c.getPackage1().getPackageId() == 2 && recommendationService.countRecommendationLastMonthOfCustomer(c.getCustomerId()) >= 30){
			modalPackageStatus = "overPackage2";
		}
		model.addAttribute("modalPackageStatus", modalPackageStatus);
		model.addAttribute("threshold", configurationService.getDouble(HDConfig.THRESHOLD, HDConfig.THRESHOLD_DEFAULT));
		return "uploadData";
	}

	@RequestMapping(value = { "/download1" }, method = RequestMethod.GET)
	public String download() {
		return "recommendationCompleted";
	}

	@ResponseBody
	@RequestMapping(value = "/processUpload", method = RequestMethod.POST)
	public String processUpload(@PathVariable("lang") Optional<String> lang,
			@RequestParam(value = "file") CommonsMultipartFile commonsMultipartFiles,
			@RequestParam("txtDate") String timerDate, @RequestParam("txtRecommendationName") String recommendationName,
			@RequestParam("txtNumOfItem") String numOfItem, @RequestParam("txtThreshold") String threshold,
			@RequestParam("txtRecordCount") String recordCount, @RequestParam(value="runEvaluation", required=false) String runEvaluation,
			@RequestParam(value = "submitCheck", required = false) String doNotRun, Model model, Principal principal) {
		String language = LanguageResolver.resolve(lang, model);
		Customer c = customerService.findByEmail(principal.getName());
		if (c == null) {
			return "redirect:/account/signin";
		}
		String nameFile = commonsMultipartFiles.getOriginalFilename();
		if (!"".equals(nameFile)) {
			long timeStamp = Calendar.getInstance().getTimeInMillis();
			String fullInputDir = InitConstant.webServerRootPath + InitConstant.UPLOADED_DIR_PATH + "/"
					+ c.getCustomerId() + "/" + timeStamp + "/input";
//			System.out.println(fullInputDir);
			File fileDir = new File(fullInputDir);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			try {
				commonsMultipartFiles.transferTo(new File(fileDir + File.separator + nameFile));
				Recommendation re = new Recommendation();
				UploadedFile uf = new UploadedFile();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
				re.setName(recommendationName);
				re.setFolderInputURL("/" + c.getCustomerId() + "/" + timeStamp + "/input");
				re.setFolderOutputURL("/" + c.getCustomerId() + "/" + timeStamp + "/output");
				try {
					re.setNumOfItem(Integer.parseInt(numOfItem));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					re.setThreshold(Double.parseDouble(threshold));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					re.setRecordCount(Integer.parseInt(recordCount));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				re.setPriority(c.getPackage1().getPackageId());

				if (doNotRun == null) {
					re.setRecommendationStatusId(E.RStatus.Submitted.getValue());
				} else {
					re.setRecommendationStatusId(E.RStatus.Waiting.getValue());

					try {
						if (!timerDate.isEmpty()) {
							re.setTimer(df.parse(timerDate));
						}
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}
				re.setRecommendTypeId(E.RType.ItemBased.getValue());
				re.setCustomer(c);
				model.addAttribute("filename", nameFile);
				recommendationService.save(re);
				uf.setFileName(nameFile);
				uf.setFileSize(commonsMultipartFiles.getSize());
				uf.setHDFSURL("/" + c.getCustomerId() + "/" + timeStamp + "/input");
				uf.setCustomer(c);
				uploadedFileService.save(uf);
				if (runEvaluation!=null) {
					Evaluation ev = new Evaluation();
					ev.setRecommendationId(re.getRecommendationId());
					evaluationService.save(ev);
				}
				if (re.getRecommendationStatusId() == E.RStatus.Submitted.getValue()) {
					HDHelper.submit2Queue(re);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Failed");
			}
		}
		if(doNotRun==null){
			return "/"+language+"/portal/recommendation/submitted?s=1";
		}else{
			return "/"+language+"/portal/recommendation/waiting?s=1";
		}
		
	}

	private static final int BUFFER_SIZE = 4096;

	@RequestMapping(value = "/download/result/{recommendationId}", method = RequestMethod.GET)
	public void downloadResult(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("recommendationId") int recommendationId, Principal principal) {
		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();
		Recommendation recommendation = recommendationService.findById(recommendationId);
		Customer c = customerService.findByEmail(principal.getName());

		if (c == null || recommendation == null) {
			try {
				String output = "Resources not found!";
				response.setContentType("text/html");
				response.setContentLength(output.length());
				OutputStream outStream = response.getOutputStream();
				outStream.write(output.getBytes());
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if (c.getCustomerId() != recommendation.getCustomer().getCustomerId()) {
			try {
				String output = "You do not have authorization on this resources!";
				response.setContentType("text/html");
				response.setContentLength(output.length());
				OutputStream outStream = response.getOutputStream();
				outStream.write(output.getBytes());
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		// construct the complete absolute path of the file
		String fullPath = InitConstant.webServerRootPath + InitConstant.RESULT_DIR_PATH
				+ recommendation.getFolderOutputURL() + "/part-r-00000";
		File downloadFile = new File(fullPath);
		
		if (!downloadFile.exists()) {
			try {
				String output = "Resources not found or was deleted!";
				response.setContentType("text/html");
				response.setContentLength(output.length());
				OutputStream outStream = response.getOutputStream();
				outStream.write(output.getBytes());
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(downloadFile);

			// get MIME type of the file
			String mimeType = context.getMimeType(fullPath);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}
			System.out.println("MIME type: " + mimeType);

			// set content attributes for the response
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			// set headers for the response
			String headerKey = "Content-Disposition";
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm");
			String headerValue = String.format("attachment; filename=\"%s\"",
					"result-" + dateFormat.format(recommendation.getCreatedTime()) + ".txt");
			response.setHeader(headerKey, headerValue);

			// get output stream of the response
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}