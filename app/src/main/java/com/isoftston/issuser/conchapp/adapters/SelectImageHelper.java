package com.isoftston.issuser.conchapp.adapters;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import com.isoftston.issuser.conchapp.model.bean.ChosenImageFile;
import com.isoftston.issuser.conchapp.utils.FileSizeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by yizh
 */
public class SelectImageHelper implements AdapterView.OnItemClickListener{

    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_GALLERY = 2;

    private int maxSize;
    private SelectImageAdapter adapter;

    private OnOpenChooseDialogCallback callback;
    private Observable.OnSubscribe<Integer> onSubscribe = new Observable.OnSubscribe<Integer>() {
        @Override public void call(final Subscriber<? super Integer> subscriber) {
            callback = new OnOpenChooseDialogCallback() {
                @Override public void onOpen(int position) {
                    subscriber.onNext(position);
                }
            };
        }
    };

    public SelectImageHelper(int maxSize, GridView display, int itemLayoutResId,
                             OnOpenChooseDialogCallback callback) {

        this(maxSize, display, itemLayoutResId);
        this.callback = callback;
    }

    public SelectImageHelper(int maxSize, AdapterView display, int itemLayoutResId) {
        this.maxSize = maxSize;

        adapter = new SelectImageAdapter(display.getContext(), itemLayoutResId, maxSize);
        display.setAdapter(adapter);
        display.setOnItemClickListener(this);

        adapter.add(ChosenImageFile.emptyInstance());
    }

    public SelectImageHelper(int maxSize, AdapterView display, int itemLayoutResId,boolean b){
        this.maxSize = maxSize;
        adapter=new SelectImageAdapter(display.getContext(), itemLayoutResId, maxSize,b);
        display.setAdapter(adapter);
        display.setOnItemClickListener(this);

        adapter.add(ChosenImageFile.emptyInstance());
    }

    public List<File> getChosenImages() {
        List<ChosenImageFile> files = adapter.getData();
        List<File> result = new ArrayList<>();
        for (ChosenImageFile file : files) {
            if (file.chosen)
                if(!file.fromSet)
                result.add(file.image);
        }
        return result;
    }

    public List<String> getSetUrls(){
        List<ChosenImageFile> files = adapter.getData();
        List<String> result = new ArrayList<>();
        for (ChosenImageFile file : files) {
            if (file.chosen)
                if(file.fromSet)
                    result.add(file.imageUrl);
        }
        return result;
    }

    public void setImageUrls(String[] images){

        List<ChosenImageFile> files=new ArrayList<>();
        for (int i = 0; i <images.length; i++) {
            ChosenImageFile imageFile=new ChosenImageFile(true,images[i],true);
            files.add(imageFile);
        }
        if (files.size()<maxSize){
            files.add(ChosenImageFile.emptyInstance());
        }
        adapter.replaceAll(files);
    }

    public void setPicFiles(List<String> list){
        List<ChosenImageFile> files=new ArrayList<>();
        for (int i = 0; i <list.size(); i++) {
            ChosenImageFile imageFile=new ChosenImageFile(true,new File(list.get(i)));
            files.add(imageFile);
        }
        if (files.size()<maxSize){
            files.add(ChosenImageFile.emptyInstance());
        }

        adapter.replaceAll(files);
    }

    public void setImageUrls(List<String> images){

        List<ChosenImageFile> files=new ArrayList<>();
        for (int i = 0; i <images.size(); i++) {
            ChosenImageFile imageFile=new ChosenImageFile(true,images.get(i),true);
            files.add(imageFile);
        }
        if (files.size()<maxSize){
            files.add(ChosenImageFile.emptyInstance());
        }
        adapter.replaceAll(files);
    }


    private FunctionConfig setupFunctionConfig(int maxSize) {
        return new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(false)
                .setEnablePreview(false)
                .setEnableRotate(false)
                .setEnableCrop(false)
                .setMutiSelectMaxSize(maxSize)
                .build();
    }

    public String picName="";
    public void setPicName(String name){
        this.picName=name;
    }

    private FunctionConfig setupFunctionConfig() {
        return setupFunctionConfig(1);
    }

    public Observable<Integer> toObservable() {
        return Observable.create(onSubscribe);
    }

    public interface OnOpenChooseDialogCallback {
        void onOpen(final int position);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (adapter.getItem(position).chosen) return;
        if (callback != null) callback.onOpen(position);
    }

    public void openGallery(final int position, final String photoName) {
        GalleryFinal.openGalleryMuti(REQUEST_GALLERY, setupFunctionConfig(maxSize - position),
                new GalleryFinal.OnHanlderResultCallback() {

                    @Override
                    public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {
                        int size = resultList.size();
                        for (int i = 0; i < size; i++) {
                            PhotoInfo info = resultList.get(i);
                            if (i == 0)
                                adapter.remove(position);

                            if (adapter.getCount() <= maxSize)
                                adapter.add(new ChosenImageFile(true,
                                        new File(info.getPhotoPath()), photoName));

                            if (i == size - 1 && adapter.getCount() < maxSize)
                                adapter.add(ChosenImageFile.emptyInstance());
                        }
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {
                    }
                });
    }

    public void openCamera(final int position, final String photoName) {
        GalleryFinal.openCamera(REQUEST_CAMERA, setupFunctionConfig(),
                new GalleryFinal.OnHanlderResultCallback() {

                    @Override
                    public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {
                        PhotoInfo info = resultList.get(0);
                        adapter.remove(position);
                        if (adapter.getCount() <= maxSize)
                            adapter.add(new ChosenImageFile(true, new File(info.getPhotoPath()), photoName));

                        if (adapter.getCount() < maxSize)
                            adapter.add(ChosenImageFile.emptyInstance());
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {
                        Log.e("yzh","---"+errorMsg);
                    }
                });
    }

}
