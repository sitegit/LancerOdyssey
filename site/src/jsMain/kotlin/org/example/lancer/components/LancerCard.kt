package org.example.lancer.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.ButtonSize
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import org.example.lancer.styles.ButtonStyle
import org.example.lancer.styles.SocialIconStyle
import org.example.lancer.util.Res
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun LancerCard(colorMode: ColorMode) {
    val breakpoint = rememberBreakpoint()

    SimpleGrid(
        modifier = Modifier
            .fillMaxWidth(
                if (breakpoint <= Breakpoint.MD) 100.percent else Res.Dimens.MAX_CARD_WIDTH.px
            )
            .thenIf(
                condition = breakpoint > Breakpoint.MD,
                other = Modifier.height(Res.Dimens.MAX_CARD_HEIGHT.px)
            )
            .boxShadow(
                color = Colors.Black.copy(alpha = 10),
                blurRadius = 50.px,
                spreadRadius = 50.px
            )
            .padding(all = 12.px)
            .borderRadius(r = Res.Dimens.BORDER_RADIUS.px)
            .background(
                if (colorMode.isLight) Colors.White else
                Res.Theme.DARK_BLUE.color
            ),
        numColumns = numColumns(base = 1, md = 2)
    ) {
        LeftSide(colorMode, breakpoint)
        RightSide(breakpoint)
    }
}

@Composable
private fun LeftSide(
    colorMode: ColorMode,
    breakpoint: Breakpoint
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 50.px),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = if (breakpoint <= Breakpoint.SM)
            Alignment.CenterHorizontally else Alignment.Start
    ) {
        SpanText(
            text = Res.String.NAME,
            modifier = Modifier
                .margin(bottom = 12.px)
                .fontFamily(Res.String.ROBOTO_CONDENSED)
                .color(if (colorMode.isLight) Colors.Black else Colors.White)
                .fontSize(50.px)
                .fontWeight(FontWeight.Bold)
                .textAlign(
                    if (breakpoint <= Breakpoint.SM) TextAlign.Center
                    else TextAlign.Start
                )
        )
        SpanText(
            text = Res.String.CATCH_PHRASE,
            modifier = Modifier
                .margin(bottom = 24.px)
                .fontFamily(Res.String.ROBOTO_REGULAR)
                .color(if (colorMode.isLight) Colors.Black else Colors.White)
                .fontSize(18.px)
                .textAlign(
                    if (breakpoint <= Breakpoint.SM) TextAlign.Center
                    else TextAlign.Start
                )
        )
        Surface(
            modifier = Modifier
                .height(4.px)
                .width(40.px)
                .margin(bottom = 24.px)
                .background(
                    if (colorMode.isLight) Res.Theme.BLUE.color
                    else Res.Theme.LIGHT_BLUE.color
                )
                .align(
                    if (breakpoint <= Breakpoint.SM) Alignment.CenterHorizontally
                    else Alignment.Start
                )
        ) {}
        SpanText(
            modifier = Modifier
                .fontFamily(Res.String.ROBOTO_REGULAR)
                .fontSize(14.px)
                .color(if (colorMode.isLight) Colors.Black else Colors.White)
                .opacity(50.percent)
                .lineHeight(2)
                .margin(bottom = 36.px)
                .textAlign(
                    if (breakpoint <= Breakpoint.SM) TextAlign.Center
                    else TextAlign.Start
                ),
            text = Res.String.DESCRIPTION
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .gap(16.px),
            horizontalArrangement = if (breakpoint <= Breakpoint.SM)
                Arrangement.Center else Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = ButtonStyle.toModifier(),
                size = ButtonSize.LG,
                onClick = { window.location.href = Res.String.EMAIL }
            ) {
                Image(
                    modifier = Modifier.margin(right = 12.px),
                    src = if (colorMode.isLight) Res.Icon.EMAIL_LIGHT
                    else Res.Icon.EMAIL_DARK
                )
                SpanText(
                    modifier = Modifier
                        .margin(top = 1.px)
                        .fontSize(14.px)
                        .color(
                            if (colorMode.isLight) Colors.White
                            else Res.Theme.GRADIENT_ONE_DARK.color
                        )
                        .fontWeight(FontWeight.Bold),
                    text = Res.String.BUTTON_TEXT
                )
            }

            Row (
                modifier = Modifier.gap(12.px)
            ) {
                SocialIcon.entries.filter {
                    if (colorMode.isLight) !it.name.contains("Light")
                    else it.name.contains("Light")
                }.forEach {
                    IconButton(
                        modifier = SocialIconStyle.toModifier(),
                        colorMode = colorMode,
                        icon = it.icon,
                        link = it.link
                    )
                }
            }
        }
    }
}

@Composable
private fun RightSide(breakpoint: Breakpoint) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .thenIf(
                condition = breakpoint > Breakpoint.MD,
                other = Modifier.height((Res.Dimens.MAX_CARD_HEIGHT - 24).px)
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .objectFit(ObjectFit.Cover),
            src = Res.Image.PHOTO_LANCER
        )
    }
}