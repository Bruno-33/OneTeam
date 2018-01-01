package com.example.administrator.oneteam.Utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.administrator.oneteam.R;
import com.example.administrator.oneteam.Utils.Adapters.ContactAdapter;
import com.example.administrator.oneteam.model.Contact;
import com.example.administrator.oneteam.tools.PinYin;
import com.example.administrator.oneteam.tools.QuickIndexBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D105-01 on 2017/12/24.
 */

public class ContactFragment extends Fragment {

    private ListView lv_contact;
    private QuickIndexBar qib;
    private TextView tv_center;
//    private EditText et_search;
    private TextView tv_no_contact;

    private ArrayAdapter<Contact> adapter;
//    private ArrayAdapter<Contact> searchAdapter;
    private List<Contact> list = new ArrayList<Contact>();
    public static final Uri URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private SectionIndexer mIndexer;
//    private SearchTask searchTask;

    /**
     * 定义字母表的排序规则
     */
    private String alphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static ContactFragment newInstance(){
        return new ContactFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment, null);

        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_CONTACTS)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this.getActivity(), new  String[]{Manifest.permission.READ_CONTACTS}, 1);
            bindViews(view);
        }
        else{
            bindViews(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void bindViews(View view){

        list.clear();
        lv_contact = (ListView) view.findViewById(R.id.lv_contact);
        qib = (QuickIndexBar) view.findViewById(R.id.qib);
        tv_center = (TextView) view.findViewById(R.id.tv_center);
        tv_no_contact = (TextView) view.findViewById(R.id.tv_no_contact);
        getContactArray();
        adapter = new ContactAdapter(this.getContext(), R.layout.contact_item, list, mIndexer);
        lv_contact.setAdapter(adapter);
        qib.setOnIndexChangeListener(new QuickIndexBar.OnIndexChangeListener() {
            @Override
            public void onIndexChange(int section) {
                int position = mIndexer.getPositionForSection(section);
                lv_contact.setSelection(position);
                tv_center.setText(QuickIndexBar.INDEX_ARRAYS[section]);
                tv_center.setVisibility(View.VISIBLE);
            }

            @Override
            public void onActionUp() {
                tv_center.setVisibility(View.GONE);
            }
        });

    }

    private List<Contact> getContactArray() {
        Cursor cursor = getActivity().getContentResolver().query(URI,
                new String[] { "display_name", "sort_key", "phonebook_label", ContactsContract.CommonDataKinds.Phone.NUMBER},
                null, null, "phonebook_label");
        Contact contact;
        if (cursor.moveToFirst()) {
            do {
                contact = new Contact();

                String contact_name = cursor.getString(0);
                String phonebook_label = cursor.getString(2);
                String phone_number = cursor.getString(3);

                contact.setPhonebookLabel(getPhonebookLabel(phonebook_label));
                contact.setPinyinName(PinYin.getPinYin(contact_name));
                contact.setName(contact_name);
                contact.setPhoneNumber(phone_number);

                list.add(contact);
            } while (cursor.moveToNext());
        }
        // 实例化indexer
        mIndexer = new AlphabetIndexer(cursor, 2, alphabet);
        return list;
    }

    private String getPhonebookLabel(String phonebook_label) {
        if (phonebook_label.matches("[A-Z]")) {
            return phonebook_label;
        }
        return "#";
    }
}
