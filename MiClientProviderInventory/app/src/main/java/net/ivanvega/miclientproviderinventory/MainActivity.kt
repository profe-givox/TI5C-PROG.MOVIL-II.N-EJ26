package net.ivanvega.miclientproviderinventory

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import net.ivanvega.miclientproviderinventory.ui.theme.MiClientProviderInventoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiClientProviderInventoryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally  ){

        Button(
            onClick = {
                val cursor = ctx.contentResolver.query(
                    Uri.parse( InventoryContract.ItemsContract.CONTENT_URI.toString() + "/3"),
                    null, null, null, null

                )

                while (cursor!!.moveToNext()) {
                       val id = cursor.getInt(0)
                       val name = cursor.getString(1)
                       val price = cursor.getDouble(2)
                    Log.i("CursorXXX", "id: $id, name: $name, price: $price")
                }
            }
        ) {
            Text(text = "Traer Inventory from Provider")
        }

    }
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MiClientProviderInventoryTheme {
        Greeting("Android")
    }
}