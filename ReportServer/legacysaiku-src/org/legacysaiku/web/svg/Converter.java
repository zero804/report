/*
 *  ReportServer
 *  Copyright (c) 2007 - 2020 InfoFabrik GmbH
 *  http://reportserver.net/
 *
 *
 * This file is part of ReportServer.
 *
 * ReportServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package org.legacysaiku.web.svg;

/**
 * @author Tomasz Nurkiewicz
 * @since 1/15/13, 10:29 AM
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.image.TIFFTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.fop.svg.PDFTranscoder;

public abstract class Converter
{

    abstract public void convert(InputStream in, OutputStream out, Integer size) throws IOException, TranscoderException;

    private final String contentType;
    private final String extension;

    protected Converter(String contentType, String extension)
    {
        this.contentType = contentType;
        this.extension = extension;
    }

    public String getContentType()
    {
        return contentType;
    }

    public String getExtension()
    {
        return extension;
    }

    public static Converter byType(String type)
    {
        if (type.equals("SVG")) { return new SvgConverter(); }
        if (type.equals("PNG")) { return new PngConverter(); }
        if (type.equals("TIFF")) { return new TiffConverter(); }
        if (type.equals("JPG")) { return new JpgConverter(); }
        if (type.equals("PDF")) { return new PdfConverter(); }
        return null;
    }
}

class SvgConverter extends Converter
{

    public SvgConverter()
    {
        super("image/svg+xml", "svg");
    }

    public void convert(InputStream in, OutputStream out, Integer size) throws IOException
    {
        IOUtils.copy(in, out);
    }

}

abstract class BatikConverter extends Converter
{

    protected BatikConverter(String extension)
    {
        super("image/" + extension, extension);
    }

    protected BatikConverter(String contentType, String extension)
    {
        super(contentType, extension);
    }

    public void convert(InputStream in, OutputStream out, Integer size) throws TranscoderException
    {
        Transcoder t = createTranscoder();
        if (size != null)
        {
            final float sizeBound = Math.max(Math.min(size, 2000.0f), 32.0f);
            t.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, sizeBound);
        }
        t.transcode(new TranscoderInput(in), new TranscoderOutput(out));
    }

    protected abstract Transcoder createTranscoder();

}

class PngConverter extends BatikConverter
{

    public PngConverter()
    {
        super("png");
    }

    protected Transcoder createTranscoder() { return new PNGTranscoder();}

}

class JpgConverter extends BatikConverter
{

    public JpgConverter()
    {
        super("image/jpeg", "jpg");
    }

    protected Transcoder createTranscoder()
    {
        Transcoder t = new JPEGTranscoder();
        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 0.95f);
        return t;
    }

}

class TiffConverter extends BatikConverter
{

    public TiffConverter()
    {
        super("tiff");
    }

    protected Transcoder createTranscoder() {return new TIFFTranscoder();}

}

class PdfConverter extends BatikConverter
{

    public PdfConverter()
    {
        super("application/pdf", "pdf");
    }

    protected Transcoder createTranscoder() {return new PDFTranscoder();}

}