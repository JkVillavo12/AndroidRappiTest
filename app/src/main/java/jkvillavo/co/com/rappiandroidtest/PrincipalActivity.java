package jkvillavo.co.com.rappiandroidtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jkvillavo.co.com.rappiandroidtest.adapter.AdapterCategories;
import jkvillavo.co.com.rappiandroidtest.commons.Commons;

public class PrincipalActivity extends AppCompatActivity implements AdapterCategories.IOnCategoryAction {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.principal_textInicio)
    TextView principalTextInicio;
    @BindView(R.id.principal_recycler)
    RecyclerView principalRecycler;

    private AdapterCategories adapterCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        List<String> stringList = new ArrayList<>();
        stringList.add(getResources().getString(R.string.title_populares));
        stringList.add(getResources().getString(R.string.title_topRated));
        stringList.add(getResources().getString(R.string.title_upComing));

        adapterCategories = new AdapterCategories(this, stringList);

        principalRecycler.setAdapter(adapterCategories);
        principalRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        principalRecycler.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void IOnCategoryAction_show(int position) {

        Intent intent = new Intent(PrincipalActivity.this, MoviesListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Commons.BundleKeys.BUNDLE_CATEGORY_TYPE, position);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
