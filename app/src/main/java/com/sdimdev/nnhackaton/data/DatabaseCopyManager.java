package com.sdimdev.nnhackaton.data;

import android.content.Context;
import android.os.Environment;

import com.sdimdev.nnhackaton.data.persistence.Const;
import com.sdimdev.nnhackaton.data.persistence.DataBaseProvider;
import com.sdimdev.nnhackaton.utils.system.other.FileUtils;

import java.io.File;

import io.reactivex.Completable;

public class DatabaseCopyManager {
    private Context context;
    private DataBaseProvider dataBaseProvider;

    public DatabaseCopyManager(Context context, DataBaseProvider dataBaseProvider) {
        this.context = context;
        this.dataBaseProvider = dataBaseProvider;
    }

    public Completable copyToRoot() {
        return Completable.fromAction(() -> {
            dataBaseProvider.disconnect();
            File sdcardDir = Environment.getExternalStorageDirectory();
            File mobileDbName = new File(sdcardDir, Const.MOBILE_DB_NAME);
            String mobileDb = mobileDbName.getAbsolutePath();
            String mobilePath = context.getDatabasePath(Const.MOBILE_DB_NAME).getPath();
            FileUtils.copyFile(mobilePath, mobileDb);
            dataBaseProvider.init();
        });

    }
}
