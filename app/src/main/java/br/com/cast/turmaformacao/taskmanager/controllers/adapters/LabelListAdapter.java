package br.com.cast.turmaformacao.taskmanager.controllers.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import br.com.cast.turmaformacao.taskmanager.R;
import br.com.cast.turmaformacao.taskmanager.model.entities.Label;

public class LabelListAdapter extends BaseAdapter {

    private Activity context;
    private List<Label> labels;

    public LabelListAdapter(Activity context, List<Label> labels) {
        this.context = context;
        this.labels = labels;
    }

    @Override
    public int getCount() {
        return labels.size();
    }

    @Override
    public Label getItem(int position) {
        return labels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = context.getLayoutInflater().inflate(R.layout.list_item_label, parent, false);

        View viewLabel = (View) view.findViewById(R.id.viewLabel);
        int hexColor = android.graphics.Color
                .parseColor(getItem(position).getColor().getHex());
        viewLabel.setBackgroundColor(hexColor);

        TextView textViewLabelName = (TextView) view.findViewById(R.id.textViewLabelName);
        textViewLabelName.setText(" - " + getItem(position).getName());

        return view;
    }

    public void setItens(List<Label> itens) {
        labels.clear();
        labels.addAll(itens);
    }
}
