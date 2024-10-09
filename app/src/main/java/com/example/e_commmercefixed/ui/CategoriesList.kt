package com.example.e_commmercefixed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commmercefixed.models.products.Category

@Composable
fun CategoriesList(categories: List<Category>) {
    LazyRow {
        items(
            categories,
            key = { category ->
                category.id
            }
        ) {
            CategoryCard(category = it)
        }
    }
}

@Composable
fun CategoryCard(category: Category) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = category.image,
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                        )
                    )
            ) {
                Text(
                    text = category.name,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OnError(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text("Error loading image", color = Color.White)
    }
}

