import org.jspecify.annotations.NullMarked;

@NullMarked
module dev.siebrenvde.ntfy {
    requires static transitive org.jspecify;
    requires static transitive org.jetbrains.annotations;
    requires transitive java.net.http;

    exports dev.siebrenvde.ntfy;
    exports dev.siebrenvde.ntfy.message;
    exports dev.siebrenvde.ntfy.topic;
}
