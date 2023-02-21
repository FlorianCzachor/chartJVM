import io.data2viz.charts.chart.chart
import io.data2viz.charts.chart.constant
import io.data2viz.charts.chart.mark.MarkCurves
import io.data2viz.charts.chart.mark.line
import io.data2viz.charts.chart.mark.plot
import io.data2viz.charts.chart.quantitative
import io.data2viz.color.Colors
import io.data2viz.geom.Size
import io.data2viz.shape.Symbols
import io.data2viz.viz.newVizContainer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

import org.w3c.fetch.Response
import kotlinx.browser.window
import kotlin.js.Promise

private val width = 800.0
private val height = 500.0

private val valuex = listOf(1.0, 2.0, 3.0, 4.0, 5.0)

data class Weather(
    val month: Int,
    val avgTemp: Double)

private fun parseWeather(row: List<String>) = Weather(
    row[0].toInt(),
    row[1].toDouble()
)

private val months = listOf("Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec.")

fun main() {
    Application.launch(MyFirstChart::class.java)
}

class MyFirstChart: Application() {

    override fun start(stage: Stage) {

        // Creating the JFX root element, a Pane
        val root = Pane()

        // Creating a VizContainer to hold our chart
        root.newVizContainer().run {

            // Size the VizContainer
            size = Size(width, height)

            val request: Promise<Response> =
                window.fetch("https://docs.google.com/spreadsheets/d/e/2PACX-1vTX4QuCNyDvUoAwk6Jl6UJ4r336A87VIKQ5BVyEgowXG_raXdFBMvmUhmz1LLc07GavyC9J6pZ4YHqJ/pub?gid=650761999&single=true&output=csv")

            request.then {
                it.text().then {

                    // Parse all result, keep only one city and one year
                    val results = Dsv()
                        .parseRows(it)
                        .drop(1)
                        .map { parseWeather(it) }

                    // Create a first simple Chart
                    chart(values) {
                        val values = quantitative({domain.valuex})

                        line(valuex, valuey) {

                            curve = MarkCurves.Curved
                            size = constant(30.0)
                            marker = constant(Symbols.Circle)
                            showMarkers = true

                            strokeColor = constant(Colors.Web.black)
                            strokeColorHighlight = constant(Colors.Web.black)
                            strokeWidth = constant(2.0)

                            y {
                                start = .0
                                end = 80.0
                            }
                        }
                    }
                }
            }

            // Launch our Scene
            stage.apply {
                title = "My first chart!"
                scene = (Scene(root, width, height))
                show()
            }
        }
    }
}