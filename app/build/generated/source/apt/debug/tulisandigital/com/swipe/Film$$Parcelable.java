
package tulisandigital.com.swipe;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2017-11-15T15:40-0800")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Film$$Parcelable
    implements Parcelable, ParcelWrapper<tulisandigital.com.swipe.Film>
{

    private tulisandigital.com.swipe.Film film$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Film$$Parcelable>CREATOR = new Creator<Film$$Parcelable>() {


        @Override
        public Film$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Film$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Film$$Parcelable[] newArray(int size) {
            return new Film$$Parcelable[size] ;
        }

    }
    ;

    public Film$$Parcelable(tulisandigital.com.swipe.Film film$$2) {
        film$$0 = film$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(film$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(tulisandigital.com.swipe.Film film$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(film$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(film$$1));
            parcel$$1 .writeString(film$$1 .foto);
            parcel$$1 .writeString(film$$1 .author);
            parcel$$1 .writeString(film$$1 .description);
            parcel$$1 .writeString(film$$1 .date_post);
            parcel$$1 .writeString(film$$1 .title);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public tulisandigital.com.swipe.Film getParcel() {
        return film$$0;
    }

    public static tulisandigital.com.swipe.Film read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            tulisandigital.com.swipe.Film film$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            film$$4 = new tulisandigital.com.swipe.Film();
            identityMap$$1 .put(reservation$$0, film$$4);
            film$$4 .foto = parcel$$3 .readString();
            film$$4 .author = parcel$$3 .readString();
            film$$4 .description = parcel$$3 .readString();
            film$$4 .date_post = parcel$$3 .readString();
            film$$4 .title = parcel$$3 .readString();
            tulisandigital.com.swipe.Film film$$3 = film$$4;
            identityMap$$1 .put(identity$$1, film$$3);
            return film$$3;
        }
    }

}
