package com.ginfoclique.aquastar;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
	
	// give your server registration url here
    static final String SERVER_URL = "http://ginfoclique.com/mkce_campus/register_gcm.php";
    static final String QUESTION_URL = "http://ginfoclique.com/mkce_campus/get_question_info.php";

    // Google project id for GCM retrived from the Google console (address bar)
    static final String SENDER_ID = "434375220247";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "Ginfo GCM";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.androidhive.pushnotifications.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "MSG";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
