package com.gbasem.geosql;

import com.gbasem.geosql.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


public class AddActivity extends Activity implements OnClickListener {
	static String DATABASE_NAME="userdata";
	public static final String TABLE_NAME="user";
	public static final String KEY_FNAME="fname";
	public static final String KEY_LNAME="lname";
	public static final String KEY_ID="id";
	public static final String KEY_MODE="";
	private Button btn_save;
	String CREATE_TABLE;
private EditText edit_first,edit_last;
private DbHelper mHelper;
private SQLiteDatabase dataBase;
private String id,fname,lname;
private boolean isUpdate;
private String[] mystring;//creating a string array named mystring
Spinner samplespinner; //Assigning a name for spinner
String[] DataToDB;//defining a string array named DataToDB
String[]result_array;//defining an array for saving the results obtained from DB
String Selecteditem;//Defining a string for storing selected item from spinner

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        
        btn_save=(Button)findViewById(R.id.save_btn);
        edit_first=(EditText)findViewById(R.id.frst_editTxt);
        edit_last=(EditText)findViewById(R.id.last_editTxt);
        
///////////////////SPINNER/////////////////////////
		
ArrayAdapter sampleadapter;//Assigning a name for ArrayAdapter

Resources res = getResources();//Assigning a name for Resources

mystring = res.getStringArray(R.array.modes);//getting the array items to string named my string
//mystring is an array which is defined on the top

samplespinner= (Spinner) findViewById(R.id.spinner1); //samplespinner is defined in the top       
//samplespinner is the name given to the spinner at the top

sampleadapter = new ArrayAdapter<String>(this,
                              android.R.layout.simple_spinner_item, mystring);
samplespinner.setAdapter(sampleadapter);

samplespinner.setOnItemSelectedListener(new OnItemSelectedListener()
	{
     public void onItemSelected(AdapterView<?> arg0, View arg1,
                                              int arg2, long arg3)
		{
    	 
		//Toast.makeText(getBaseContext(), spVIA.getSelectedItem().toString(),
//		Toast.LENGTH_LONG).show(); 
		
		Selecteditem = samplespinner.getSelectedItem().toString();
		CREATE_TABLE="INSERT INTO user values(1,'hello',"+Selecteditem+")";
		
		
         
		
		}
              public void onNothingSelected(AdapterView<?> arg0)
              {
                              // TODO Auto-generated method stub                 
              }
	});	
//////////////////////////SPINNER ENDS///////////////////////////////////////////////////
        
       isUpdate=getIntent().getExtras().getBoolean("update");
        if(isUpdate)
        {
        	id=getIntent().getExtras().getString("ID");
        	fname=getIntent().getExtras().getString("Fname");
        	lname=getIntent().getExtras().getString("Lname");
        	
        	edit_first.setText(fname);
        	edit_last.setText(lname);
        	
        }
         
         btn_save.setOnClickListener(this);
         
         mHelper=new DbHelper(this);
        
    }

    // saveButton click event 
	public void onClick(View v) {
		fname=edit_first.getText().toString().trim();
		lname=edit_last.getText().toString().trim();
		if(fname.length()>0 && lname.length()>0)
		{
			saveData();
			
		}
		else
		{
			AlertDialog.Builder alertBuilder=new AlertDialog.Builder(AddActivity.this);
			alertBuilder.setTitle("Invalid Data");
			alertBuilder.setMessage("Please, Enter valid data");
			alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
					
				}
			});
			alertBuilder.create().show();
		}
		
	}

	/**
	 * save data into SQLite
	 */
	private void saveData(){
		dataBase=mHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		
		values.put(DbHelper.KEY_FNAME,fname);
		values.put(DbHelper.KEY_LNAME,Selecteditem );
		
		System.out.println("");
		if(isUpdate)
		{    
			//update database with new data 
			dataBase.update(DbHelper.TABLE_NAME, values, DbHelper.KEY_ID+"="+id, null);
		}
		else
		{
			//insert data into database
			dataBase.insert(DbHelper.TABLE_NAME, null, values);
			Toast.makeText(getBaseContext(),(String)CREATE_TABLE, 
	                Toast.LENGTH_SHORT).show();
		}
		//close database
		dataBase.close();
		finish();
		
		
	}

}
