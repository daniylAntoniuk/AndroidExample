package com.example.smarthome.fragments.stores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.smarthome.Network.AuthorizedService;
import com.example.smarthome.Network.ImageRequester;
import com.example.smarthome.Network.models.Categories;
import com.example.smarthome.Network.models.GetStores;
import com.example.smarthome.Network.models.Profile;
import com.example.smarthome.Network.models.Store;
import com.example.smarthome.Network.models.StoreEntry;
import com.example.smarthome.Network.utils.CommonUtils;
import com.example.smarthome.Network.utils.GetClassCode;
import com.example.smarthome.R;
import com.example.smarthome.constants.Urls;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoresFragment extends Fragment {
    private List<Categories> categories;
    private List<StoreEntry> storeEntryList;
    private StoreCardRecyclerViewAdapter storeAdapter;
    private RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_stores, container, false);
        CommonUtils.showLoading(getContext());
        AuthorizedService.getInstance()
                .getJSONApi()
                .categories()
                .enqueue(new Callback<List<Categories>>() {
                    @Override
                    public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                        if (response.errorBody() == null && response.isSuccessful()) {
                            List<Categories> categories = response.body();
                            GetClassCode.init(categories);
                            List<String> names
                                    =categories.stream().map(x->x.getName()).collect(Collectors.toList());
                            int dataArray =0;
//                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                                    view.getContext(), dataArray, android.R.layout.simple_spinner_item);
                            ArrayAdapter<String>adapter = new ArrayAdapter<String>(view.getContext(),
                                    android.R.layout.simple_spinner_item,names);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            Spinner s = view.findViewById(R.id.spinner);
//                            AutoCompleteTextView editTextFilledExposedDropdown =
//                                    view.findViewById(R.id.filled_exposed_dropdown);
                            s.setAdapter(adapter);
                            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String Text = s.getSelectedItem().toString();
                                    String value = GetClassCode.getCode(Text);
                                    loadStoresList(Integer.parseInt(value));
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            //editTextFilledExposedDropdown.setText("Магазини",false);
                            //editTextFilledExposedDropdown.setAdapter(adapter);
                            //editTextFilledExposedDropdown.setEnabled(false);
                            //CommonUtils.hideLoading();
                        }
                        else {
                            CommonUtils.hideLoading();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Categories>> call, Throwable t) {
                        CommonUtils.hideLoading();

                    }
                });
        setupViews(view);

        setRecyclerView();

        loadStoresList(0);

        return view;
    }
    private void setupViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                GridLayoutManager.VERTICAL, false));
        storeEntryList = new ArrayList<>();
        storeAdapter = new StoreCardRecyclerViewAdapter(storeEntryList,  getContext());

        recyclerView.setAdapter(storeAdapter);

//        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
//        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        //recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }
    private void loadStoresList(int id) {
       // CommonUtils.showLoading(getContext());

        AuthorizedService.getInstance()
                .getJSONApi()
                .stores(new GetStores(id))
                .enqueue(new Callback<List<Store>>() {
                    @Override
                    public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                        if (response.errorBody() == null && response.isSuccessful()) {
                            List<Store> list = response.body();
                            storeEntryList.clear();
                            for (Store item : list) {
                                StoreEntry tmp = new StoreEntry(item.getId(), item.getName(), item.getImage(), item.getAdress(), item.getDescription(),item.getCoordinate1(),item.getCoordinate2());
                                storeEntryList.add(tmp);
                            }
                            storeAdapter.notifyDataSetChanged();
                            CommonUtils.hideLoading();
                        }
                        else {
                            CommonUtils.hideLoading();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Store>> call, Throwable t) {
                        CommonUtils.hideLoading();
                    }
                });
    }
}
