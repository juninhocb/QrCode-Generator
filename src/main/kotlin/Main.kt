import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

@Throws(Exception::class)
fun generateQRCode(url: String, width: Int, height: Int) {
    val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
    hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H

    val qrCodeWriter = QRCodeWriter()
    val bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height, hints)

    val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bufferedImage.setRGB(x, y, if (bitMatrix[x, y]) Color.BLACK.rgb else Color.WHITE.rgb)
        }
    }

    val outputFile = File("qr-code.png")
    ImageIO.write(bufferedImage, "png", outputFile)
}

fun generateQRCodeWithPDF(pdfData: ByteArray, width: Int, height: Int): BufferedImage {
    val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
    hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H

    val qrCodeWriter = QRCodeWriter()
    val bitMatrix = qrCodeWriter.encode(String(pdfData), BarcodeFormat.QR_CODE, width, height, hints)

    val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bufferedImage.setRGB(x, y, if (bitMatrix[x, y]) Color.BLACK.rgb else Color.WHITE.rgb)
        }
    }

    val outputFile = File("qr-code-pdf.png")
    ImageIO.write(bufferedImage, "png", outputFile)

    return bufferedImage
}



fun main(args: Array<String>) {

    val home = System.getProperty("user.dir")
    val pdfResourceFile = File("$home/calsan.pdf")
    val pdfData = pdfResourceFile.readBytes()
    val url = "http://localhost/myphp-test"
    val width = 300
    val height = 300

    generateQRCode(url, width, height)
    println("QR Code generated successfully.")
}