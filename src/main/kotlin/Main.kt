import io.data2viz.charts.chart.chart
import io.data2viz.charts.chart.constant
import io.data2viz.charts.chart.discrete
import io.data2viz.charts.chart.mark.MarkCurves
import io.data2viz.charts.chart.mark.area
import io.data2viz.charts.chart.quantitative
import io.data2viz.charts.core.*
import io.data2viz.color.Colors
import io.data2viz.geom.Size
import io.data2viz.geom.point
import io.data2viz.math.pct
import io.data2viz.viz.FontWeight
import io.data2viz.viz.TextHAlign
import io.data2viz.viz.TextVAlign
import io.data2viz.viz.newVizContainer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.WritableImage
import javafx.scene.layout.Pane
import javafx.stage.Stage
import org.glavo.png.javafx.PNGJavaFXUtils
import java.io.File
import java.io.IOException
import java.nio.file.Paths
// from numpy import asarray

// JavaFX Window Size
private val width = 800.0
private val height = 300.0

// Graph Marks Values
val values = listOf(Triple(2.0, 1.0, "5A"),
                    Triple(2.0, 2.0, "4C"),
                    Triple(3.0, 3.0, "4B"),
                    Triple(4.0, 4.0, "4B"))

fun main() {
    Application.launch(MyFirstChart::class.java)
}

class MyFirstChart : Application() {

    override fun start(stage: Stage) {

        // Creating the JFX root element, a Pane
        val root = Pane()

        // Creating a VizContainer to hold our chart
        root.newVizContainer().run {

            // Size the VizContainer
            size = Size(width, height)

            // Create a first simple Chart
            chart(values) {
                val values1 = quantitative({domain.first})
                val values2 = discrete({domain.second})

                // val startYear = 2019 - 1

                // Config to disable x and y Axis
                config {
                    //yAxis { enableAxis = false }
                    //xAxis { enableAxis = false }
                    events {
                        highlightMode = HighlightMode.None
                    }
                }

                // Create Area Graph
                area(values2, values1) {

                    curve = MarkCurves.Curved
                    showMarkers = true

                    // Graph Size
                    //TODO: "val values2 = discrete({domain.second})" ignores preset Graph Size
                    y {
                        start = 0.0
                        end = 10.0
                    }
                    x {
                        start = 0.0
                        end = 10.0
                    }

                    // Color of Graph
                    strokeColor = constant(Colors.rgb(48,89,130))
                    strokeColorHighlight = constant(Colors.rgb(48,89,130))
                    strokeWidth = constant(2.0)

                    // Color below Graph
                    // TODO: Improve Gradient to support different Data
                    fill =  discrete( {
                        Colors.Gradient.linear(point(0, 0), point(0, 800))
                            .withColor(Colors.rgb(0,56,95, 80.pct), 0.pct)
                            .andColor(Colors.rgb(48,89,130,0.pct), 30.pct)
                    } )

                    // Labels above and below Marks
                    markDecorator = { datum, position, drawingZone ->

                        // Call the default decorator
                        defaultMarkDecorator(datum, position, drawingZone)

                        size = constant(80.0)

                        // Box for Above the Mark
                        drawingZone.rect {
                            x = position.x - 20.0
                            y = position.y - 40.0
                            width = 40.0
                            height = 30.0
                            fill = Colors.Web.white.withAlpha(80.pct)

                            strokeColor = Colors.Web.black.withAlpha(20.pct)
                            strokeColorHighlight = constant(Colors.rgb(48,89,130))
                            strokeWidth = 2.0
                        }

                        // Label/Text for Above the Mark
                        drawingZone.text {
                            x = position.x
                            y = position.y - 25.0
                            vAlign = TextVAlign.MIDDLE
                            hAlign = TextHAlign.MIDDLE
                            fontSize = 16.0
                            textContent = datum.domain.third
                            // TODO: Add George Font
                            fontWeight = FontWeight.BOLD
                            textColor = Colors.rgb(0,56,95)
                        }

                        // Label/Text for Below the Mark
                        drawingZone.text {
                            x = position.x
                            y = position.y + 35.0
                            vAlign = TextVAlign.MIDDLE
                            hAlign = TextHAlign.MIDDLE
                            fontSize = 14.0
                            // TODO: Data for textContent
                            textContent = "2019"
                            // textContent = startYear + values2
                            // TODO: Add George Font
                            fontWeight = FontWeight.BOLD
                            textColor = Colors.rgb(0,56,95, 60.pct)
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

        // Save Chart as PNG
        val file: File? = File("chart.png")
        if (file != null) {
            try {
                val writableImage = WritableImage(800, 300)
                root.snapshot(null, writableImage)
                // TODO: Save Canvas to Bytecode and then encode with base64
                /*
                numpydata = asarray(root)
                writeImageToArray
                base64
                 */
                PNGJavaFXUtils.writeImage(writableImage, Paths.get("chart.png"))
            } catch (ex: IOException) {
                println("Error!")
            }
        }

        // TODO: Uncomment stage.close() when done
        // stage.close()
    }
}