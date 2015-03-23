package oko.tm.avto_control;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class SettingsActivity extends Activity
{
    // это будет именем файла настроек,данных
    public static final String APP_PREFERENCES = "avto_control_settings";
    // переменная для работы с файлом
    SharedPreferences mSettings;
    // настройки, подлежащие хранению
    public static final String APP_PREFERENCES_PHONE = "phone";
    public static final String APP_PREFERENCES_PASSWORD = "password";

    EditText txtPhoneNo_;
    EditText txtPassword_;
    //для sms command
    String ini_password="1234";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //----------- работа с файлом, содержащий настройки
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        txtPhoneNo_ = (EditText) findViewById(R.id.txtPhoneNo_);
        txtPassword_ = (EditText) findViewById(R.id.txtPassword_);

    }

    @Override
    protected void onResume() //onResume()
    {
        // TODO Auto-generated method stub
        super.onResume(); //onResume();


        //-------------восстанавливаем сохраненные настройки, данные
        if(mSettings.contains(APP_PREFERENCES_PHONE)) {
            txtPhoneNo_.setText(mSettings.getString(APP_PREFERENCES_PHONE, ""));
        }

        if(mSettings.contains(APP_PREFERENCES_PASSWORD)) {
            txtPassword_.setText(mSettings.getString(APP_PREFERENCES_PASSWORD, ini_password));
        }


        //--------------------------------------------------------
    }

    @Override
    protected void onPause()
    {


        // TODO Auto-generated method stub
        super.onPause();


        //---------- Сохраняем настройки
        String phoneNo_ = txtPhoneNo_.getText().toString();
        String password_ = txtPassword_.getText().toString();

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_PHONE,phoneNo_);
        editor.putString(APP_PREFERENCES_PASSWORD,password_);

        //editor.apply();
        editor.commit();
        //----------------------------
    }


}
