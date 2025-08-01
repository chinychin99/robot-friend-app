// host the UI and nagivation
package robot-friend-app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import robot-friend-app.view.AssessmentFragment;
import robot-friend-app.view.ControllerFragment;
import robot-friend-app.view.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewByID(R.id.nav_view);
        nav.setOnItemSelectedListener(item -> {
            Fragment selected =
                    switch (item.getItemId()) {
                        case R.id.nagivation_home -> new HomeFragment();
                        case R.id.nagivation_controller -> new ControllerFragment();
                        case R.id.nagivation_assessment -> new AssessmentFragment();
                        default -> null;
                    };

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selected)
                    .commit();
            return true;
        });

        // show home by default
        nav.setSelectedItemId(R.id.navigation_home);
    }
}