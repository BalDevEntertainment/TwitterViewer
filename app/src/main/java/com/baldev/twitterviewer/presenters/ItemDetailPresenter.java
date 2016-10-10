package com.baldev.twitterviewer.presenters;

import com.baldev.twitterviewer.mvp.DataModel;
import com.baldev.twitterviewer.mvp.ItemDetailMVP;
import com.baldev.twitterviewer.mvp.ItemDetailMVP.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class ItemDetailPresenter implements ItemDetailMVP.Presenter {

	private final View view;
	private final DataModel dataModel;
	private List<Subscription> subscriptions = new ArrayList<>();

	@Inject
	public ItemDetailPresenter(ItemDetailMVP.View view, DataModel dataModel) {
		this.view = view;
		this.dataModel = dataModel;
	}


	@Override
	public void unsubscribe() {
		for (Subscription subscription : subscriptions) {
			subscription.unsubscribe();
		}
	}
}
