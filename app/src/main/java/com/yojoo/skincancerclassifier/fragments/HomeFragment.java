package com.yojoo.skincancerclassifier.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yojoo.skincancerclassifier.BuildConfig;
import com.yojoo.skincancerclassifier.Connection.ConnectionManager;
import com.yojoo.skincancerclassifier.Data.Messages;
import com.yojoo.skincancerclassifier.Data.MyResponse;
import com.yojoo.skincancerclassifier.Data.Report;
import com.yojoo.skincancerclassifier.Database.DatabaseManager;
import com.yojoo.skincancerclassifier.R;
import com.yojoo.skincancerclassifier.activity.MainActivity;
import com.yojoo.skincancerclassifier.adabter.SectionsPageAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, Callback<MyResponse> {
    private Button CameraBtn, UploadBtn;
    private ImageView imageView;
    private View fragmentView;
    String mCurrentPhotoPath;
    private File imageFile;
    private String theKey;
    DataListener callBack;
    Dialog dialog;
    private Uri selectedImageUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int GALLERY_REQUEST = 100;
    private static final int GALLERY_PERMISSION_REQUEST = 200;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        CameraBtn = fragmentView.findViewById(R.id.camera_btn_frag);
        UploadBtn = fragmentView.findViewById(R.id.upload_btn_frag);
        imageView = fragmentView.findViewById(R.id.img_frag);

        CameraBtn.setOnClickListener(this);
        UploadBtn.setOnClickListener(this);



        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_btn_frag:
                startDialog();
                break;
            case R.id.upload_btn_frag:
                // do something
                uploadimage();
                break;
        }
    }


    protected void startDialog() {
        dialog = new Dialog((getActivity()));
        dialog.setContentView(R.layout.custom_dialog);
        Objects.requireNonNull(dialog.getWindow()).getAttributes().width =
                getResources().getDisplayMetrics().widthPixels;
        Button camera = dialog.findViewById(R.id.pick_camera_Btn);
        Button gallery = dialog.findViewById(R.id.pick_gallery_Btn);
        Button deletePick = dialog.findViewById(R.id.delete_photo_Btn);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
                dialog.hide();

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                dialog.hide();

            }
        });

        deletePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(0);
                dialog.hide();

            }
        });


        dialog.show();

    }

    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALLERY_REQUEST);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data.getData();
            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                readFileFromSelectedURI();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_PERMISSION_REQUEST);
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            setPic();
        }
    }

    private void readFileFromSelectedURI() {
        Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(selectedImageUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(0);

            cursor.close();
            imageFile = new File(imagePath);
            Bitmap image = BitmapFactory.decodeFile(imagePath);
            try {
                image.compress(Bitmap.CompressFormat.JPEG, 50, new FileOutputStream(imageFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(image);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALLERY_PERMISSION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFileFromSelectedURI();
            } else {
                Toast.makeText(getActivity(), R.string.cant_read_selected_image, Toast.LENGTH_SHORT).show();
            }
        } else {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getActivity(), R.string.can_not_open_camera, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    private void uploadimage() {
        MultipartBody.Part partImage = null;
//        final ResultBottomSheetDialog bottomSheetDialog = new ResultBottomSheetDialog();
        if (mCurrentPhotoPath != null) {
            File file = new File(mCurrentPhotoPath);
            // Parsing any Media type file
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), file);
            partImage = MultipartBody.Part.createFormData("image", file.getName(), reqBody);
        }

        ConnectionManager.getInstance().uploadImage(partImage).enqueue(this);

    }

    @Override
    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                MyResponse myResponse = response.body();
                assert myResponse != null;
                theKey = myResponse.getKey();
                callBack.onDataReceived(AddReport());
                DatabaseManager.getInstance(getActivity()).insertMessage(new Messages(myResponse.getMessage(), getCurrentDate()));
                imageView.setImageResource(0);
                Toast.makeText(getActivity(), "Image sent Successfully", Toast.LENGTH_SHORT).show();
//                goToResultList();
            }
        } else {
            Toast.makeText(getActivity(), "problem uploading image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<MyResponse> call, Throwable t) {
        Log.v("Response gotten is", t.getMessage());
        Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return DateFormat.getDateInstance().format(calendar.getTime());
    }

//    private void goToResultList(){
//        ResultListFragment resultListFragment = new ResultListFragment();
//        FragmentManager manager = getFragmentManager();
//        manager.beginTransaction().replace(R.id.container,resultListFragment).commit();
//    }

    public List<Report> AddReport(){
        Report report = new Report(getCurrentDate(), theKey);
        DatabaseManager.getInstance(getActivity()).insertReport(report);
        List<Report> reports = DatabaseManager.getInstance(getActivity()).getAllReports();
        dialog.dismiss();
//        Intent refresh = new Intent(getActivity(), MainActivity.class);
//        startActivity(refresh);
//        getActivity().finish();
        return reports;
    }


    public interface DataListener{
        public void onDataReceived(List<Report> reports);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callBack = (DataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataListener");
        }
    }
}


//        @Override
//        public void onSaveInstanceState(@NonNull Bundle outState) {
//            super.onSaveInstanceState(outState);
//            outState.putString("Path", mCurrentPhotoPath);
//            outState.putSerializable("File", imageFile);
//        }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            mCurrentPhotoPath = savedInstanceState.getString("Path");
//            imageFile = (File) savedInstanceState.getSerializable("File");
//        }
//
//    }


    //        SkinAPI api = ConnectionManager.getApiServices();
//        Call<MyResponse> call = api.uploadImage(partImage);
//        call.enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        MyResponse myResponse = response.body();
//                        assert myResponse != null;
//                        Toast.makeText(getActivity(), "Your Key Is " + myResponse.getKey(), Toast.LENGTH_SHORT).show();
//                        DatabaseManager.getInstance(getActivity()).insertReport(new Report(getCurrentDate(), myResponse.getKey()));
//                        DatabaseManager.getInstance(getActivity()).insertMessage(new Messages(myResponse.getMessage(),getCurrentDate()));
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "problem uploading image", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//                Log.v("Response gotten is", t.getMessage());
//            }
//        });




