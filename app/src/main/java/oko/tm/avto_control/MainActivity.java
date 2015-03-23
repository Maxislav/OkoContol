package oko.tm.avto_control;

import android.app.AlertDialog;
//import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
//import android.text.Editable;
//import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
//import android.telephony.gsm.SmsManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.EditText;
//import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
//import org.w3c.dom.Text;
//import java.text.Format;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
//import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends ActionBarActivity {

//---------------------- Выпадающий список смс-команд
    String Command_0="Местоположение автомобиля";
    String Command_1="Состояние сигнализации";
    String Command_2="GPRS-настройки прибора";
    String Command_3="Общие настройки прибора";
    String Command_4="Рестарт прибора";
    String Command_5="Очистить буфер с данными";
    String Command_6="Включить смс-оповещение";
    String Command_7="Выключить смс-оповещение";
    String Command_8="Включить оповещение звонком";
    String Command_9="Выключить оповещение звонком";
    String Command_10="Включить GPRS-передачу данных";
    String Command_11="Выключить GPRS-передачу данных";


    String[] data = {
            Command_0,
            Command_1,
            Command_2,
            Command_3,
            Command_4,
            Command_5,
            Command_6,
            Command_7,
            Command_8,
            Command_9,
            Command_10,
            Command_11};

    String SMS_Command_0="04";
    String SMS_Command_1="02";
    String SMS_Command_2="08";
    String SMS_Command_3="09";
    String SMS_Command_4="75";
    String SMS_Command_5="73";
    String SMS_Command_6="3011111111";
    String SMS_Command_7="3000000000";
    String SMS_Command_8="3111111111";
    String SMS_Command_9="3100000000";
    String SMS_Command_10="68";
    String SMS_Command_11="69";

    public int COMMAND_POSITION = 0;
//----------------------------------------------------------

//---------------------- организация работы с файлом, где хранятся настройки
    // это будет именем файла настроек,данных
    public static final String APP_PREFERENCES = "avto_control_settings";
    // переменная для работы с файлом
    SharedPreferences mSettings;
    // настройки, подлежащие хранению
    public static final String APP_PREFERENCES_PHONE = "phone";
    public static final String APP_PREFERENCES_PASSWORD = "password";
    //public static final String APP_PREFERENCES_COMMAND = "command";

    public String PHONE_NUMBER = "";
    public String PASSWORD = "";
//------------------------------------------------------------------------


    //EditText txtPhoneNo;
    //EditText txtPassword;
    Button btn_send_command;
    ImageButton imageButton1_on;
    ImageButton imageButton1_off;
    ImageButton imageButton2_on;
    ImageButton imageButton2_off;

    String Enter_Phone_Password="Введите телефонный номер GSM-сигнализации и пароль доступа к ней";
    String Enter_Phone="Введите телефонный номер GSM-сигнализации";
    String Enter_Password="Введите пароль доступа GSM-сигнализации";

    //для sms command
    String ini_password="1234";
    String alarm_on="01";
    String alarm_off="00";
    String rele_on="06";
    String rele_off="05";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //----------- работа с файлом, содержащий настройки
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        //txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        //txtPassword = (EditText) findViewById(R.id.txtPassword);
        btn_send_command= (Button) findViewById(R.id.btn_send_command);
        imageButton1_on = (ImageButton) findViewById(R.id.imageButton1_on);
        imageButton1_off = (ImageButton) findViewById(R.id.imageButton1_off);
        imageButton2_on = (ImageButton) findViewById(R.id.imageButton2_on);
        imageButton2_off = (ImageButton) findViewById(R.id.imageButton2_off);

//--------------------------- выпадающий список смс-команд
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Список команд");
        // выделяем элемент
        spinner.setSelection(0);


        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                COMMAND_POSITION=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //--------------------------- обработчик события нажатия кнопки
        btn_send_command.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                switch (COMMAND_POSITION)
                {
                    case 0:
                        try_sendSMS(SMS_Command_0);
                        break;
                    case 1:
                        try_sendSMS(SMS_Command_1);
                        break;
                    case 2:
                        try_sendSMS(SMS_Command_2);
                        break;
                    case 3:
                        try_sendSMS(SMS_Command_3);
                        break;
                    case 4:
                        try_sendSMS(SMS_Command_4);
                        break;
                    case 5:
                        try_sendSMS(SMS_Command_5);
                        break;
                    case 6:
                        try_sendSMS(SMS_Command_6);
                        break;
                    case 7:
                        try_sendSMS(SMS_Command_7);
                        break;
                    case 8:
                        try_sendSMS(SMS_Command_8);
                        break;
                    case 9:
                        try_sendSMS(SMS_Command_9);
                        break;
                    case 10:
                        try_sendSMS(SMS_Command_10);
                        break;
                    case 11:
                        try_sendSMS(SMS_Command_11);
                        break;

                }

            }
        });
//-------------------------------------------------------
//-----------------------------------------------------




//--------------------------- обработчик события нажатия кнопки
        imageButton1_on.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try_sendSMS(alarm_on);
            }
        });
//-------------------------------------------------------

//--------------------------- обработчик события нажатия кнопки
        imageButton1_off.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try_sendSMS(alarm_off);
            }
        });
//-------------------------------------------------------

        //--------------------------- обработчик события нажатия кнопки
        imageButton2_on.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try_sendSMS(rele_on);
            }
        });
//-------------------------------------------------------

        //--------------------------- обработчик события нажатия кнопки
        imageButton2_off.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try_sendSMS(rele_off);
            }
        });
//-------------------------------------------------------
    }


/*    @Override
    protected void onPause()
    {

        super.onPause();

        //---------- Сохраняем настройки
        String phoneNo = txtPhoneNo.getText().toString();
        String password = txtPassword.getText().toString();


        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_PHONE,phoneNo);
        editor.putString(APP_PREFERENCES_PASSWORD,password);

        //editor.apply();
        editor.commit();
        //----------------------------
    }*/

    @Override
    protected void onResume() //onResume()
    {
        // TODO Auto-generated method stub
        super.onResume(); //onResume();

        //-------------считываем сохраненные настройки
        if(mSettings.contains(APP_PREFERENCES_PHONE)) {
            //txtPhoneNo.setText(mSettings.getString(APP_PREFERENCES_PHONE, ""));
            PHONE_NUMBER=mSettings.getString(APP_PREFERENCES_PHONE, "");
        }

        if(mSettings.contains(APP_PREFERENCES_PASSWORD)) {
            //txtPassword.setText(mSettings.getString(APP_PREFERENCES_PASSWORD, ini_password));
            PASSWORD=mSettings.getString(APP_PREFERENCES_PASSWORD, ini_password);
        }


        //--------------------------------------------------------
    }

    //---try sends a SMS message ---
    private void try_sendSMS (String command)
    {

        String phoneNumber = PHONE_NUMBER; //txtPhoneNo.getText().toString();
        String password = PASSWORD; //txtPassword.getText().toString();

        if (phoneNumber.length()>0 && password.length()>0)
            sendSMS(phoneNumber, password + command);
        else
        {

            if (phoneNumber.length() == 0 && password.length() > 0)
                Toast.makeText(getBaseContext(),
                        Enter_Phone,
                        Toast.LENGTH_SHORT).show();
            else if (phoneNumber.length() > 0 && password.length() == 0)
                Toast.makeText(getBaseContext(),
                        Enter_Password,
                        Toast.LENGTH_SHORT).show();
            else

                Toast.makeText(getBaseContext(),
                        Enter_Phone_Password,
                        Toast.LENGTH_SHORT).show();

            // вызываем окно ввода настроек
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }


                /* Toast toast=Toast.makeText(getBaseContext(),Enter_Phone_Password,Toast.LENGTH_LONG);
                Toast toast=Toast.makeText(context, Enter_Phone_Password, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();*/
    }


    //---sends a SMS message ---
    private void sendSMS(String phoneNumber, String message)
    {

        //----------------- таймер-сообщение
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder .setTitle("Выполнение команды")
                .setIcon(R.drawable.ic_launcher)
                .setMessage("Подождите пожалуйста...")
                .setCancelable(false);

        final AlertDialog dlg = builder.create();

        dlg.show();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                dlg.dismiss(); // when the task active then close the dialog
                timer.cancel(); // also just top the timer thread, otherwise,
                // you may receive a crash report
            }
        }, 3000); // через 3 секунд (3000 миллисекунд), the task will be active.
        //------------------------------------------

    	/*
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, test.class), 0);
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, pi, null);
        */

        String SENT = "COMMAND_SENT";
        String DELIVERED = "COMMAND_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);


        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "Команда отправлена",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));


        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "Команда доставлена",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "Команда не доставлена",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));



        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.menu_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
