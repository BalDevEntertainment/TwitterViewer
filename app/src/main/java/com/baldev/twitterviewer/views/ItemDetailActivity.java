package com.baldev.twitterviewer.views;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.baldev.twitterviewer.R;
import com.baldev.twitterviewer.components.DaggerItemDetailComponent;
import com.baldev.twitterviewer.modules.AppModule;
import com.baldev.twitterviewer.modules.ItemDetailModule;
import com.baldev.twitterviewer.mvp.ItemDetailMVP;
import com.baldev.twitterviewer.mvp.ItemDetailMVP.Presenter;
import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDetailActivity extends AppCompatActivity implements ItemDetailMVP.View {

	public static final String PHOTO_ID = "photoId";
	public static final String PHOTO_URL = "photoUri";
	public static final String PHOTO_TITLE = "photoTitle";

	@Inject
	Presenter presenter;
	@BindView(R.id.image) SimpleDraweeView photo;
	@BindView(R.id.toolbar) Toolbar toolbar;
	@BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;

	@BindView(R.id.text_view_uploaded_on) TextView uploadedOn;
	@BindView(R.id.text_item_detail_owner) TextView owner;
	@BindView(R.id.text_item_detail_description) TextView description;
	@BindView(R.id.text_item_detail_views) TextView views;
	@BindView(R.id.text_item_detail_tags) TextView tags;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_itemdetail);
		this.setSupportActionBar(toolbar);
		ButterKnife.bind(this);
		this.setupComponent();
		this.setSupportActionBar(toolbar);
		if (this.getSupportActionBar() != null) {
			this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			this.getSupportActionBar().setDisplayShowHomeEnabled(true);
		}
		this.setAlreadyRetrievedData();

		String photoId = getIntent().getStringExtra(PHOTO_ID);
	}

	protected void setupComponent() {
		DaggerItemDetailComponent.builder()
				.itemDetailModule(new ItemDetailModule(this))
				.appModule(new AppModule(this.getApplication()))
				.build()
				.inject(this);
	}

	//Butterknife doesn't have an annotation for this.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onDestroy() {
		this.presenter.unsubscribe();
		super.onDestroy();
	}

	private void setAlreadyRetrievedData() {
		String photoTitle = getIntent().getStringExtra(PHOTO_TITLE);
		String photoUrl = getIntent().getStringExtra(PHOTO_URL);
		this.photo.setImageURI(Uri.decode(photoUrl));
		collapsingToolbarLayout.setTitle(photoTitle);
	}
}
