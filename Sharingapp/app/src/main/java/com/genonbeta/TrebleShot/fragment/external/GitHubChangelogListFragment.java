package com.genonbeta.TrebleShot.fragment.external;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.genonbeta.TrebleShot.R;
import com.genonbeta.TrebleShot.config.AppConfig;
import com.genonbeta.TrebleShot.util.AppUtils;
import com.genonbeta.android.framework.app.DynamicRecyclerViewFragment;
import com.genonbeta.android.framework.widget.RecyclerViewAdapter;
import com.genonbeta.android.updatewithgithub.RemoteServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GitHubChangelogListFragment
        extends DynamicRecyclerViewFragment<GitHubChangelogListFragment.VersionObject, RecyclerViewAdapter.ViewHolder, GitHubChangelogListFragment.VersionListAdapter>
{
    @Override
    public VersionListAdapter onAdapter()
    {
        return new VersionListAdapter(getContext());
    }

    @Override
    public void onResume()
    {
        super.onResume();
        AppUtils.publishLatestChangelogSeen(getActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setEmptyImage(R.drawable.ic_github_circle_white_24dp);
        setEmptyText(getString(R.string.mesg_noInternetConnection));

        useEmptyActionButton(true);
        getEmptyActionButton().setText(R.string.butn_refresh);
        getEmptyActionButton().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                refreshList();
            }
        });

        onEnsureList();
    }

    public static class VersionObject
    {
        public String tag;
        public String name;
        public String changes;

        public VersionObject(String tag, String name, String changes)
        {
            this.tag = tag;
            this.name = name;
            this.changes = changes;
        }
    }

    public static class VersionListAdapter extends RecyclerViewAdapter<VersionObject, RecyclerViewAdapter.ViewHolder>
    {
        private List<VersionObject> mList = new ArrayList<>();
        private String mCurrentVersion;

        public VersionListAdapter(Context context)
        {
            super(context);
            mCurrentVersion = AppUtils.getLocalDevice(context).versionName;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new ViewHolder(getInflater().inflate(R.layout.list_changelog, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position)
        {
            final VersionObject versionObject = getList().get(position);
            ImageView imageCheck = holder.getView().findViewById(R.id.image_check);
            TextView text1 = holder.getView().findViewById(R.id.text1);
            TextView text2 = holder.getView().findViewById(R.id.text2);

            text1.setText(versionObject.name);
            text2.setText(versionObject.changes);

            imageCheck.setVisibility(mCurrentVersion.equals(versionObject.tag)
                    ? View.VISIBLE
                    : View.GONE);
        }

        @Override
        public List<VersionObject> onLoad()
        {
            List<VersionObject> versionObjects = new ArrayList<>();

            try {


            } catch (Exception e) {
                e.printStackTrace();
            }

            return versionObjects;
        }

        @Override
        public void onUpdate(List<VersionObject> passedItem)
        {
            synchronized (getList()) {
                getList().clear();
                getList().addAll(passedItem);
            }
        }

        @Override
        public long getItemId(int i)
        {
            return 0;
        }

        @Override
        public int getItemCount()
        {
            return getList().size();
        }

        @Override
        public List<VersionObject> getList()
        {
            return mList;
        }
    }
}

