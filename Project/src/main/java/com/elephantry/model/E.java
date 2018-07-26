package com.elephantry.model;

public class E {

	public enum Role {
		ROOT("ROLE_ROOT"), ADMIN("ROLE_ADMIN"), CUSTOMER("ROLE_CUST");
		private final String value;

		private Role(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

	public enum RStatus {
		Waiting(1), Submitted(2), Running(3), Completed(4), Failed(5), Deleted(6);

		private final int value;
		

		private RStatus(int value) {
			this.value = value;
		}
		
		
		public static String getName(int value){
			return RStatus.values()[value - 1].toString();
		}
		
		public int getValue() {
			return value;
		}
		
	}

	public enum RPriority {
		LOW(1), NORMAL(2), HIGH(3);
		private final int value;

		private RPriority(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	public enum NType {
		RStartRunning(1), RFinished(2), RFailed(3);
		private final int value;

		private NType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}
	
	public enum RType {
		ItemBased(1);
		private final int value;

		private RType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}
}
