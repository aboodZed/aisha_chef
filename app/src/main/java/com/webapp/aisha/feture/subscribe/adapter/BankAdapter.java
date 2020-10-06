package com.webapp.aisha.feture.subscribe.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Bank;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankHolder> {

    private Activity activity;
    private ArrayList<Bank> banks;
    private BankListener bankListener;
    private String position = "";

    public BankAdapter(Activity activity, ArrayList<Bank> banks) {
        this.activity = activity;
        this.banks = banks;
    }

    @NonNull
    @Override
    public BankHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BankHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BankHolder holder, int position) {
        holder.setData(banks.get(position));
    }

    @Override
    public int getItemCount() {
        return banks.size();
    }

    public void setBankListener(BankListener bankListener) {
        this.bankListener = bankListener;
    }

    class BankHolder extends RecyclerView.ViewHolder {

        @BindView((R.id.ll_bank)) LinearLayout llBank;
        @BindView(R.id.tv_bank) TextView tvBank;

        private Bank bank;

        public BankHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(Bank bank) {
            this.bank = bank;
            tvBank.setText(bank.getBank_name());
            if (position.equals(bank.getIban())){
                llBank.setBackground(activity.getDrawable(R.drawable.yellow_button));
            }else {
                llBank.setBackground(activity.getDrawable(R.drawable.white_button));
            }
        }

        @OnClick(R.id.ll_bank)
        public void getData() {
            position = bank.getIban();
            notifyDataSetChanged();
            bankListener.selectBank(bank);
        }
    }

    public interface BankListener {
        void selectBank(Bank bank);
    }
}
