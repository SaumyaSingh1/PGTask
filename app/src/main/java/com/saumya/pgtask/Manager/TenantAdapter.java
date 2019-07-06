package com.saumya.pgtask.Manager;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.saumya.pgtask.R;

import java.util.ArrayList;

public class TenantAdapter extends ArrayAdapter<TenantDataModel> {
    private ArrayList<TenantDataModel> dataModels;

    public TenantAdapter(Context context, int resource, ArrayList<TenantDataModel> dataModels) {
        super(context, resource, dataModels);
        this.dataModels = dataModels;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.activity_tenant_adapter, null);
        }
        TenantDataModel tenantDataModel = dataModels.get(position);
        if (tenantDataModel != null) {
            TextView tenantName = v.findViewById(R.id.tvName);
            TextView tenantPhone = v.findViewById(R.id.tvPhone);
            TextView tenantAmount = v.findViewById(R.id.tvAmount);
            TextView tenantRoom = v.findViewById(R.id.tvRoom);
            tenantName.setText(tenantDataModel.getName());
            tenantAmount.setText(tenantDataModel.getRentAmount());
            tenantPhone.setText(tenantDataModel.getPhone());
            tenantRoom.setText(tenantDataModel.getRoomNo());
        }
        return v;
    }
}
