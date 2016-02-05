package com.gildder.invenbras.gestionactivos.views;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gildder.invenbras.gestionactivos.R;
import com.gildder.invenbras.gestionactivos.fragments.Inventarios;
import com.gildder.invenbras.gestionactivos.fragments.Notificaciones;

public class FragmentTabsActivity extends Activity implements TabListener {
    ActionBar actionBar;

    /**
     *  Arreglo de fragmentos
     */
    private Fragment[] fragments = new  Fragment[]{
            new Inventarios(),
           // new Notificaciones()
    };


    /*  METODOS DEL Ativity */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tabs);

        setTabs();

        //manejador de fragmentos
        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        for (Fragment fragment : fragments){
            fragmentTransaction.add(R.id.RltMyInventario,fragment).hide(fragment);
        }

        fragmentTransaction.show(fragments[0]).commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_inventario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionCargarInventario) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* METODOS DEL TabsListener */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        //oculta todos los fragmentos
        for (Fragment fragment:fragments){
            fragmentTransaction.hide(fragment);
        }

        fragmentTransaction.show(fragments[tab.getPosition()]);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }


    /*  Metodos implementados */

    /**
     * Este metodo adiciona tabs al actionBar
     */
    private void setTabs(){
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText("Inventarios").setTabListener(this).setIcon(R.drawable.ic_action_inventario));
       // actionBar.addTab(actionBar.newTab().setText("Notificaciones").setTabListener(this).setIcon(R.drawable.ic_action_notificacion));
    }
}
