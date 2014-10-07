package com.lin.notetaker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListNotesActivity extends ActionBarActivity {
	
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//return super.onContextItemSelected(item);
		AdapterContextMenuInfo info =(AdapterContextMenuInfo)item.getMenuInfo();
		notes.remove(info.position);
		populateList();
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater= getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_CANCELED)
		{
			return;
		}
		
		if(resultCode == EditNoteActivity.RESULT_DELETE)
		{
			notes.remove(editingNoteId);
			editingNoteId=-1;
			populateList();
		}
		
		super.onActivityResult(requestCode, resultCode, data);
		
		Serializable extra=data.getSerializableExtra("Note");
		if(extra != null)
		{
			Note newNote=(Note)extra;
			if (editingNoteId>-1)
			{
				notes.set(editingNoteId, newNote);
				editingNoteId=-1;
			}
			else
			{
				notes.add(newNote);
			}
			populateList();
		}
		
	}

	private List<Note> notes= new ArrayList<Note>();
	private ListView notesListView;
	private int editingNoteId=-1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_notes);
		notesListView = (ListView)findViewById(R.id.notesListView);
		
		notesListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int itemNumber, long id) {
				Intent editNoteIntent=new Intent(view.getContext(), EditNoteActivity.class);
				editNoteIntent.putExtra("Note", notes.get(itemNumber));
				editingNoteId=itemNumber;
				startActivityForResult(editNoteIntent,1);
				
			}
			
		});
		
		registerForContextMenu(notesListView);
		
		/*notes.add(new Note("First note", "vv", new Date()));
		notes.add(new Note("Second note", "vv", new Date()));
		notes.add(new Note("Third note", "vv", new Date()));
		notes.add(new Note("Fourth note", "vv", new Date()));
		notes.add(new Note("Fifth note", "vv", new Date()));*/
		
		populateList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_notes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		//notes.add(new Note("added note", "vv", new Date()));
		
		//populateList();
		
		Intent editNoteIntent= new Intent(this, EditNoteActivity.class);
		startActivity(editNoteIntent);
		startActivityForResult(editNoteIntent, 1);
		
		return true;
		
		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);*/
	}

	private void populateList() {
		
		
		List<String> values= new ArrayList<String>();
		
		for (Note note: notes){
			values.add(note.getTitle());
		}
		
		
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
		
		notesListView.setAdapter(adapter);
	}
}
