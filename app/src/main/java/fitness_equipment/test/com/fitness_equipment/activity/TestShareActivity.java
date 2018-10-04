package fitness_equipment.test.com.fitness_equipment.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import fitness_equipment.test.com.fitness_equipment.BasActivity;
import fitness_equipment.test.com.fitness_equipment.R;
import fitness_equipment.test.com.fitness_equipment.photo.BitmapUtils;

public class TestShareActivity extends BasActivity {


    @BindView(R.id.img_test)
    ImageView imgTest;

    @Override
    protected int getContentView() {
        return R.layout.activity_test_share;
    }

    @Override
    protected void init() {

        Intent intent =getIntent();
        String filePath=  intent.getStringExtra("path");
        Log.i("------->截图路径","路径："+filePath);
//        showPic(filePath);
        Bitmap bmp = BitmapFactory.decodeFile(filePath);
        File file = new File(filePath);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
        try {
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgTest.setImageBitmap(bmp);

    }

    private void showPic( String newpath) {
        // TODO Auto-generated method stub
        File file = new File(newpath);
//        if (file.exists()) {
//            Bitmap bm = BitmapFactory.decodeFile(newpath);
//            /**
//             * 选择变换
//             * @param origin
//             * 原图
//             * @param alpha
//             * 旋转角度，可正可负
//             * @return 旋转后的图片
//             */
//            int newwidth = bm.getWidth();
//            int newheight = bm.getHeight();
//            Matrix newmatrix = new Matrix();
//            newmatrix.preScale(1, 1);
//            Bitmap newnewBM = Bitmap.createBitmap(bm, 0,newheight/4, newwidth,
//                    newheight/2, newmatrix, false);

        BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
        Bitmap bitmap = bitmapUtils.decodeFile(newpath);
        imgTest.setImageBitmap(bitmap);


    }

}
