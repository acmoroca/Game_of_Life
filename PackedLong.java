package uk.ac.cam.acm239.prejava.ex2;

public class PackedLong{
        public static boolean get(long packed, int position) {
                long check = 1L << position;
                check &= packed;
                return(check !=  0);
        }

        public static long set(long packed, int position, boolean value) {
                if (value){
                        long filter = 1L << position;
                        filter |= packed;
                        return(filter);
                }
                else {
                        long filter = ~(1L << position);
                        filter &= packed;
                        return(filter);
                }
        }
}


