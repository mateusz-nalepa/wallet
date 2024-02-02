package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MySuccessDialog
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnDarkPreview
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview

@Composable
fun ImportSummaryStateless(importV1Summary: ImportV1Summary) {
    Column {
        ImportSummaryRowStateless("Categories total", importV1Summary.numberOfCategories)
        ImportSummaryRowStateless("Expenses total", importV1Summary.numberOfExpenses)
        ImportSummaryRowStateless("Imported categories", importV1Summary.numberOfImportedCategories)
        ImportSummaryRowStateless("Skipped categories", importV1Summary.numberOfSkippedCategories)
        ImportSummaryRowStateless("Imported expenses", importV1Summary.numberOfImportedExpenses)
        ImportSummaryRowStateless("Skipped expenses", importV1Summary.numberOfSkippedExpenses)
    }
}

@Composable
private fun ImportSummaryRowStateless(
    text: String,
    number: Int,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
    ) {
        Text(text = text)
        Text(text = "$number")
    }
    Divider()
}

@Preview(showBackground = true)
@Composable
fun MySuccessDialogDarkPreview(@PreviewParameter(ImportV1SummaryProvider::class) importV1Summary: ImportV1Summary) {
    SetContentOnDarkPreview {
        MySuccessDialog(
            onClose = {},
        ) {
            ImportSummaryStateless(importV1Summary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MySuccessDialogLightPreview(@PreviewParameter(ImportV1SummaryProvider::class) importV1Summary: ImportV1Summary) {
    SetContentOnLightPreview {
        MySuccessDialog(
            onClose = {},
        ) {
            ImportSummaryStateless(importV1Summary)
        }
    }
}

class ImportV1SummaryProvider : PreviewParameterProvider<ImportV1Summary> {
    override val values: Sequence<ImportV1Summary> =
        sequenceOf(
            ImportV1Summary(
                10,
                20,
                2,
                1,
                4,
                2
            )
        )
}
