package com.example.smarthome;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.smarthome.Network.AuthorizedService;
import com.example.smarthome.Network.ImageRequester;
import com.example.smarthome.Network.Tokens;
import com.example.smarthome.Network.models.Goods;
import com.example.smarthome.Network.models.GoodsEntry;
import com.example.smarthome.Network.models.LastOrder;
import com.example.smarthome.constants.Urls;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of App Widget functionality.
 */
public class OrderStatus extends AppWidgetProvider {
    private static ImageRequester imageRequester;

    static void getLastOrder(RemoteViews views,AppWidgetManager appWidgetManager,int appWidgetId){
        AuthorizedService.getInstance()
                .getJSONApi()
                .lastOrder()
                .enqueue(new Callback<LastOrder>() {
                    @Override
                    public void onResponse(Call<LastOrder> call, Response<LastOrder> response) {
                        if(response.isSuccessful()) {
                            LastOrder order = response.body();
                            views.setTextViewText(R.id.appwidget_text,order.getAdress()+", "+order.getHouse());
                            //imageRequester = ImageRequester.getInstance();
                            views.setTextViewText(R.id.widget_name,order.getGoodsName());
                            views.setTextViewText(R.id.widget_status,"Status: "+order.getStatus());
                            //views.setImageViewUri(R.id.widget_image, Uri.parse(Urls.BASE_URL+"/UserImages/"+order.getGoodsImage()));
                            //imageRequester.setImageFromUrl(R.id.widget_image, Urls.BASE_URL+"/UserImages/"+order.getGoodsImage());

                            appWidgetManager.updateAppWidget(appWidgetId, views);
                        }
                        else {
                            views.setTextViewText(R.id.appwidget_text,"Unauthorized");
                        }
                    }

                    @Override
                    public void onFailure(Call<LastOrder> call, Throwable t) {
                        //     Log.i("b","d");
                    }
                });
    }
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        SharedPreferences prefs=context.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.order_status);
        getLastOrder(views,appWidgetManager,appWidgetId);

        HubConnection hubConnection = HubConnectionBuilder.create(Urls.BASE_URL+"/hubs/chat").withAccessTokenProvider(Single.defer(() -> {
            return Single.just(token);
        })).build();

        hubConnection.on("orderStatusChanged", (chatId) -> {
            System.out.println("New Message: " + chatId);
            getLastOrder(views,appWidgetManager,appWidgetId);
        }, String.class);

        hubConnection.start();

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}