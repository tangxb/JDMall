package com.m520it.jdmallv2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.bean.OrderListBean;
import com.m520it.jdmallv2.protocol.IOrderChangeListener;

public class WaitPayOrderAdapter extends OrderAdapter{
	
	private IOrderChangeListener mListener;
	
	public void setListener(IOrderChangeListener mListener) {
		this.mListener = mListener;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	class ViewHolder{
		public TextView orderNOTv;
		public TextView orderStatusTv;
		public LinearLayout pcontainerLl;
		public TextView priceTv;
		public Button doBtn;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, null);
			holder=new ViewHolder();
			holder.orderNOTv=(TextView) convertView.findViewById(R.id.order_no_tv);
			holder.orderStatusTv=(TextView) convertView.findViewById(R.id.order_state_tv);
			holder.pcontainerLl=(LinearLayout) convertView.findViewById(R.id.p_container_ll);
			holder.priceTv=(TextView) convertView.findViewById(R.id.price_tv);
			holder.doBtn=(Button) convertView.findViewById(R.id.do_btn);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final OrderListBean bean = mDatas.get(position);
		holder.orderNOTv.setText("订单编号:"+bean.getOrderNum());
		holder.orderNOTv.setText("订单编号:"+bean.getOrderNum());
		holder.priceTv.setText("￥ "+bean.getTotalPrice());
		initProductsContainer(holder.pcontainerLl,bean.getItems());
		initStatus(holder.orderStatusTv,bean.getStatus());
		holder.doBtn.setVisibility(View.VISIBLE);
		holder.doBtn.setText("去 支 付");
		holder.doBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				一个简单的跳转
				if (mListener!=null) {
					mListener.onChanged(bean.getTn());
				}
			}
		});
		return convertView;
	}

	private void initStatus(TextView orderStatusTv, int status) {
//		状态关联到按钮 根据不同状态 需要不同点击事件 
		switch (status) {
		case -1:
			orderStatusTv.setText("取消订单");
			break;
		case 0:
			orderStatusTv.setText("待支付");
			break;
		case 1:
			orderStatusTv.setText("待发货");
			break;
		case 2:
			orderStatusTv.setText("待收货 ");
			break;
		case 3:
			orderStatusTv.setText("完成交易 ");
			break;
		}
	}


}
