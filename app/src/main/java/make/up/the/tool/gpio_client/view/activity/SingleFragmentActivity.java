package make.up.the.tool.gpio_client.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import make.up.the.tool.gpio_client.R;

/**
 * @author Anatolii Nosenko
 * @version 30 April 2018
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected FragmentManager fragmentManager;

    protected abstract Fragment getFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container_for_fragment);

        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.container_for_fragment, getFragment())
                    .commit();
        }
    }
}
