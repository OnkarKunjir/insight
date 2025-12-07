package com.cat.bamboo.insight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cat.bamboo.insight.transaction.Transaction
import com.cat.bamboo.insight.transaction.TransactionManager
import com.cat.bamboo.insight.ui.theme.InsightTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InsightTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            // TODO: handle add new transaction
                        }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = getString(R.string.new_transaction)
                            )
                        }
                    }
                ) { innerPadding ->
                    Transactions(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewApp() {
    generateTransactions()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Transactions(Modifier.padding(innerPadding))
    }
}

fun generateTransactions() {
    for (i in 0..10) {
        TransactionManager.addTransaction(
            Transaction(
                i,
                "Transaction#$i",
                (100 * i).toDouble(),
                System.currentTimeMillis()
            )
        )
    }
}

@Composable
fun Transactions(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        for (transaction in TransactionManager.getTransactions()) {
            TransactionCard(transaction)
        }
    }
}

@Composable
fun TransactionCard(transaction: Transaction, modifier: Modifier = Modifier) {
    val amount = TransactionManager.currencyFormat.format(transaction.amount)
    val timestamp = Instant.ofEpochMilli(transaction.timestamp)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("h:mm a EEE, dd-MM-yyyy"))
    Card(
        modifier = modifier
            .height(110.dp),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = transaction.description, fontSize = 22.sp)
                Text(text = timestamp)
            }
            Text(text = amount, fontSize = 25.sp)
        }
    }
}