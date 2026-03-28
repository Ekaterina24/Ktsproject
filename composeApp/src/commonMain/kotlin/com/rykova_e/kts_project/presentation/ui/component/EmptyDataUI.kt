package com.rykova_e.kts_project.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.rykova_e.kts_project.presentation.theme.padding_12
import com.rykova_e.kts_project.presentation.theme.padding_4
import ktsproject.composeapp.generated.resources.Res
import ktsproject.composeapp.generated.resources.empty_list
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyDataUI(
    modifier: Modifier = Modifier,
    modifierImage: Modifier = Modifier
) {
    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding_12),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = modifierImage
                    .clip(RoundedCornerShape(padding_4))
                    .size(150.dp),
                model = "https://yandex-images.clstorage.net/5R0IxP184/3fac13ZlPBz/z2-1IdmeB3-mC8RBZ4hXdCpP00gBLmgoU5KBlBMNORfXUQZq6dKDri2HA-IBa2MRlonWIbm6IRLIeuosnpwx84l5bHLKj9T5Ys0BXSsBG-8jxFxDSaIwLZKUE1SUW67U2NrUJrYAHHA9W1ZkA6UpkwdNHiyxtSCJ3_NjZVAdc_IDNGexmFIJMyDOThpmzNqrrwoUQUWqNqXT1pbcEIuokRRXiqx5TRd4eZhcYUxtLt7ISt_ofTUuStSzKKsX1PE_iXjieNuRTj-oDEQZ6QoeKSkEkAJJqv4iE13X1JKJ6BUbnY_ncYicOLyY0OzL52GWCcDfZLz5ZE7SdGxsVhV0OUUzZ_Sa38ex54vJm2YNUuGhipuNQiq5KVif1B_bBKmd3ByAL_eDGrA0Vl1pQyejVUbClux6ualF3nSrINLa-3XIseO52lFH-2TLClSoANYhqgpUzcorsGFeFlDeV4ZkF9-fx-a5gJe381RSqs1qYl3ORh6qNrDsihY8o2cem7e2TnwisdJfzHavy8aebknSKiHJHshD7vIrHtNfFBhJKFoSX4-jvE0dPrgSXiGD7-9dBQ8VKHX5IAvSeyzhGBO5ewa8oz8VkUy5bM0JFKmMW28uw5KPhmx671-RkJbQyaMfH1TALLZMm3Mx0hJpS6spXs_GlqXyuGmK0PnmKZHb8bGGvC32FRvE_-hDCNPtRd4nJY3SxYSh8m8alRfYXk4jXB4RSebwAh84_lpca4mradpDx1pgu7AlzJE6JCAf13R2TzKod93YhjFtTUFeLArd6aeJ3AdJrXEm3JVSlFsN5hwUUAEsOwscNvAc1GqJp-OfQEoWprOzrsEVO-Rn2ds4_09yZbySEQRwZ8WI3qAL0GYmwpVHhmx4YdFdWhnezizX25dJ4_PPVT4-XhZoSihtU47DWa8-dGPK2X7n7xnS-D7Ns6670lbLMyAMQ1EmyBCjbgIRRYgrf6ZRnl7RX8",
                contentDescription = "image"
            )
            Text(
                text = stringResource(Res.string.empty_list),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun EmptyDataUIPreview() {
    EmptyDataUI(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        modifierImage = Modifier.background(Color.LightGray)
    )
}