package com.token.utilities;

public class TokenUtil {
	// if token life time is null, then default token expiry value is 5 minutes
		public static final long DEFAULT_TOKEN_EXPIRY = 300000;

		/**
		 * @author Nikhil Bharadwaj Ramashasthri
		 * @since October-2017
		 * @version 1.0
		 */
		public enum TokenStatus
		{
			INITIAL(0), DELETED(-2), USED(-1);
			private int statusId;

			TokenStatus(int statusId)
			{
				this.statusId = statusId;
			}

			public int getStatusId()
			{
				return statusId;
			}

		}
}
