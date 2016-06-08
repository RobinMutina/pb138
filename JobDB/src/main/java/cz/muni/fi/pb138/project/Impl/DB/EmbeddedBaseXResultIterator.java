package cz.muni.fi.pb138.project.Impl.DB;

import org.basex.core.Context;
import org.basex.core.cmd.Open;
import org.basex.io.serial.Serializer;
import org.basex.query.QueryException;
import org.basex.query.QueryProcessor;
import org.basex.query.iter.Iter;
import org.basex.query.value.item.Item;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by xtomasch on 6/2/16.
 */
public class EmbeddedBaseXResultIterator implements Iterator, AutoCloseable{
    private QueryProcessor proc;
    private Iter iter;
    private Serializer ser;
    private ByteArrayOutputStream stream;
    private Item current;
    private boolean hasNext;


    public EmbeddedBaseXResultIterator(String query, Context context) throws QueryException, IOException{

        proc = new QueryProcessor(query, context);

            // Store the pointer to the result in an iterator:
            iter = proc.iter();
            stream = new ByteArrayOutputStream();
            // Create a serializer instance
            ser = proc.getSerializer(stream);

        current = iter.next();
        if (current == null){
            hasNext = false;
        }else{
            hasNext = true;
        }
    }

    @Override
    public String next() {
        try{
            stream.reset();
            ser.serialize(current);
            current = iter.next();
            if (current == null){
                hasNext = false;
            }else{
                hasNext = true;
            }
            return stream.toString();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public void forEachRemaining(Consumer action) {
        while(this.hasNext){
            action.accept(this.next());
        }
    }

    @Override
    public void close() throws Exception {
        if (proc != null){
            proc.close();
        }
        if (ser != null){
            ser.close();
        }
    }
}
