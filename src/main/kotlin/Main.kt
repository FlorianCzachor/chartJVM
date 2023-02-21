import io.data2viz.charts.chart.chart
import io.data2viz.charts.chart.constant
import io.data2viz.charts.chart.mark.MarkCurves
import io.data2viz.charts.chart.mark.line
import io.data2viz.charts.chart.mark.plot
import io.data2viz.charts.chart.quantitative
import io.data2viz.charts.core.LayoutPosition
import io.data2viz.charts.core.MarkLabel
import io.data2viz.color.Colors
import io.data2viz.geom.Size
import io.data2viz.math.ticks
import io.data2viz.shape.Symbols
import io.data2viz.viz.newVizContainer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

private val width = 800.0
private val height = 500.0

val values = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
//val values2 = listOf(2.0, 3.0, 4.0, 5.0, 3.0)

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

            // Create a first simple Chart
            chart(values) {
                val values = quantitative({domain})
                //val values2 = quantitative({domain.values2})

                config {
                    yAxis { enableAxis = false }
                    xAxis { enableAxis = false }
                    mark { label = MarkLabel.XY }

                }

                line(values, values) {

                    curve = MarkCurves.Curved
                    size = constant(30.0)
                    marker = constant(Symbols.Circle)
                    showMarkers = true

                    strokeColor = constant(Colors.Web.black)
                    strokeColorHighlight = constant(Colors.Web.black)
                    strokeWidth = constant(2.0)
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