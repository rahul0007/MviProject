package org.example.project.user.component

import androidx.compose.runtime.Composable
import org.example.project.domain.model.User
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import coil3.compose.AsyncImage

@Composable
fun UserListItem(user: User, modifier: Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*UserImage(
                url = user.pictureUrl,
                modifier = Modifier
                    .size(60.dp) // Set consistent size for image
            )*/

            AsyncImage(
                model = user.pictureUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    user.fullName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(user.email, style = MaterialTheme.typography.bodyMedium)
                Text(user.phone, style = MaterialTheme.typography.bodySmall)
                Text("${user.city}, ${user.country}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
