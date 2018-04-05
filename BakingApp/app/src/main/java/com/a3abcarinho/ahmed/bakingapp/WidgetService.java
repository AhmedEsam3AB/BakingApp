package com.a3abcarinho.ahmed.bakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ahmed on 18/01/18.
 */

public class WidgetService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngRemovteViewsFactory(this.getApplicationContext(),intent);
    }

}
