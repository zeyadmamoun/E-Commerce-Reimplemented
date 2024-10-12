package com.example.e_commmercefixed.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commmercefixed.R
import com.example.e_commmercefixed.models.products.Product

@Composable
fun ProductsGrid(products: List<Product>){
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(
            products,
            key = {product -> product.id}
        ){
            ProductCard(product = it)
        }
    }
}


@Composable
fun ProductCard(product: Product) {
    Card(
        shape = RoundedCornerShape(16.dp), // Slightly more rounded corners for a modern look
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.product_card_background_color)
        ),
        modifier = Modifier
            .width(220.dp)
            .height(340.dp)
            .padding(8.dp),
//        elevation = CardDefaults.cardElevation(4.dp) // Added elevation for depth
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
//                    .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = product.images[0],
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop,
                )
            }

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
            )  {
                // Product Name
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.product_card_text_color)
                    ),
                    maxLines = 1, // Restrict to a single line
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Product Description
                Text(
                    text = product.description,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall.copy(
                        lineHeight = 14.sp
                    ),
                    maxLines = 2, // Restrict description to 2 lines
                )

                Spacer(modifier = Modifier.weight(1f)) // Spacer to push price and button to the bottom

                // Price and Add to Cart Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Price
                    Text(
                        text = product.price.toString(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.product_card_price_color)
                        ),
                    )

                    // Add to Cart Button
                    IconButton(
                        onClick = { /* Add to Cart logic */ },
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp)) // Rounded corners for the button
                            .background(colorResource(id = R.color.buttons_color)) // Button background
                            .size(40.dp) // Reduce size of the button to make it more compact
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add to Cart",
                            tint = Color.White, // Icon color
                        )
                    }
                }
            }
        }
    }
}