package com.example.denis.varyag;

import android.net.Uri;

import com.dbflow5.annotation.Database;
import com.dbflow5.annotation.Table;
import com.dbflow5.config.DBFlowDatabase;
import com.dbflow5.contentprovider.annotation.ContentProvider;
import com.dbflow5.contentprovider.annotation.ContentType;
import com.dbflow5.contentprovider.annotation.ContentUri;
import com.dbflow5.contentprovider.annotation.TableEndpoint;

@ContentProvider(authority = AppDatabase.AUTHORITY,
        database = AppDatabase.class,
        baseContentUri = AppDatabase.BASE_CONTENT_URI)

@Database(version = AppDatabase.VERSION)
public abstract class AppDatabase extends DBFlowDatabase{

    public static final int VERSION = 1;
    public static final String AUTHORITY = "com.example.denis.varyag.provider";
    public static final String BASE_CONTENT_URI = "content://";

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = Uri.parse(AppDatabase.BASE_CONTENT_URI + AppDatabase.AUTHORITY).buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    // Declare endpoints here
    @TableEndpoint(name = eventProviderModel.ENDPOINT, contentProvider = AppDatabase.class)
    public static class eventProviderModel {

        public static final String ENDPOINT = "EventModel";

        @ContentUri(path = eventProviderModel.ENDPOINT,
                type = ContentType.VND_MULTIPLE + ENDPOINT)
        public static final Uri CONTENT_URI = buildUri(ENDPOINT);
    }
}
