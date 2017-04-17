package com.m520it.jdmallv2.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.activity.ProductDetailsActivity;
import com.m520it.jdmallv2.cons.NetworkConst;

public class ProductDetailsView extends Fragment {
	
	private WebView mWebView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_product_details, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mWebView=(WebView)getActivity().findViewById(R.id.webview);
		long pid = ((ProductDetailsActivity)getActivity()).mPid;
		
		mWebView.loadUrl(NetworkConst.PRODUCT_DETAILS+"?productId="+pid);
		mWebView.getSettings().enableSmoothTransition();
		mWebView.getSettings().setJavaScriptEnabled(true);
		
		mWebView.setWebViewClient(new WebViewClient(){
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			
		});
		
	}
	
}
