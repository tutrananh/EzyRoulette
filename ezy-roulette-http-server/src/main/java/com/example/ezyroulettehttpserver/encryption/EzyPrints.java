package com.example.ezyroulettehttpserver.encryption;

import java.util.Arrays;

public final class EzyPrints {
    private static final char[] HEX_ARRAY = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_ARRAY_LOWERCASE = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private EzyPrints() {
    }

    public static String print(Object object) {
        if (object == null) {
            return "null";
        } else if (object instanceof boolean[]) {
            return Arrays.toString((boolean[])((boolean[])object));
        } else if (object instanceof byte[]) {
            return Arrays.toString((byte[])((byte[])object));
        } else if (object instanceof char[]) {
            return Arrays.toString((char[])((char[])object));
        } else if (object instanceof double[]) {
            return Arrays.toString((double[])((double[])object));
        } else if (object instanceof float[]) {
            return Arrays.toString((float[])((float[])object));
        } else if (object instanceof int[]) {
            return Arrays.toString((int[])((int[])object));
        } else if (object instanceof long[]) {
            return Arrays.toString((long[])((long[])object));
        } else if (object instanceof short[]) {
            return Arrays.toString((short[])((short[])object));
        } else {
            return object instanceof Object[] ? Arrays.toString((Object[])((Object[])object)) : object.toString();
        }
    }

    public static String printBits(byte[] bytes) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < bytes.length; ++i) {
            builder.append(printBits(bytes[i]));
        }

        return builder.toString();
    }

    public static String printBits(byte value) {
        String str = insertBegin(Integer.toBinaryString(value & 255), "0", 8);
        return str.substring(str.length() - 8);
    }

    public static String insertBegin(String str, String ch, int maxlen) {
        StringBuilder builder = new StringBuilder(str);

        while(builder.length() < maxlen) {
            builder.insert(0, ch);
        }

        return builder.toString();
    }

    public static String printHex(byte[] bytes) {
        return printHex(bytes, HEX_ARRAY);
    }

    public static String printHexLowercase(byte[] bytes) {
        return printHex(bytes, HEX_ARRAY_LOWERCASE);
    }

    public static String printHex(byte[] bytes, char[] hexArray) {
        char[] hexChars = new char[bytes.length * 2];

        for(int i = 0; i < bytes.length; ++i) {
            int v = bytes[i] & 255;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 15];
        }

        String answer = new String(hexChars);
        return answer;
    }

    public static String print2d(int[][] table) {
        StringBuilder builder = new StringBuilder();
        EzyPrints.Array2DPrinter printer = new EzyPrints.Array2DPrinter(builder);
        printer.print(table);
        return builder.toString();
    }

    public static String print2d(String[][] table) {
        StringBuilder builder = new StringBuilder();
        EzyPrints.Array2DPrinter printer = new EzyPrints.Array2DPrinter(builder);
        printer.print(table);
        return builder.toString();
    }

    public static final class Array2DPrinter {
        private static final char BORDER_KNOT = '+';
        private static final char HORIZONTAL_BORDER = '-';
        private static final char VERTICAL_BORDER = '|';
        private static final String DEFAULT_AS_NULL = "(NULL)";
        private final StringBuilder out;
        private final String asNull;

        public Array2DPrinter(StringBuilder out) {
            this(out, "(NULL)");
        }

        public Array2DPrinter(StringBuilder out, String asNull) {
            if (out == null) {
                throw new IllegalArgumentException("No print stream provided");
            } else if (asNull == null) {
                throw new IllegalArgumentException("No NULL-value placeholder provided");
            } else {
                this.out = out;
                this.asNull = asNull;
            }
        }

        public void print(int[][] table) {
            String[][] strss = new String[table.length][];

            for(int i = 0; i < table.length; ++i) {
                strss[i] = new String[table[i].length];

                for(int k = 0; k < table[i].length; ++k) {
                    strss[i][k] = String.valueOf(table[i][k]);
                }
            }

            this.print(strss);
        }

        public void print(String[][] table) {
            if (table == null) {
                throw new IllegalArgumentException("No tabular data provided");
            } else if (table.length != 0) {
                int[] widths = new int[this.getMaxColumns(table)];
                this.adjustColumnWidths(table, widths);
                this.printPreparedTable(table, widths, this.getHorizontalBorder(widths));
            }
        }

        private void printPreparedTable(String[][] table, int[] widths, String horizontalBorder) {
            int lineLength = horizontalBorder.length();
            this.out.append(horizontalBorder).append("\n");
            int index = 0;
            String[][] var6 = table;
            int var7 = table.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                String[] row = var6[var8];
                if (row != null) {
                    this.out.append(this.getRow(row, widths, lineLength)).append("\n").append(horizontalBorder);
                    if (index++ < table.length - 1) {
                        this.out.append("\n");
                    }
                }
            }

        }

        private String getRow(String[] row, int[] widths, int lineLength) {
            StringBuilder builder = (new StringBuilder(lineLength)).append('|');
            int maxWidths = widths.length;

            for(int i = 0; i < maxWidths; ++i) {
                builder.append(padRight(this.getCellValue(safeGet(row, i, (String)null)), widths[i])).append('|');
            }

            return builder.toString();
        }

        private String getHorizontalBorder(int[] widths) {
            StringBuilder builder = new StringBuilder(256);
            builder.append('+');
            int[] var3 = widths;
            int var4 = widths.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                int w = var3[var5];

                for(int i = 0; i < w; ++i) {
                    builder.append('-');
                }

                builder.append('+');
            }

            return builder.toString();
        }

        private int getMaxColumns(String[][] rows) {
            int max = 0;
            String[][] var3 = rows;
            int var4 = rows.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String[] row = var3[var5];
                if (row != null && row.length > max) {
                    max = row.length;
                }
            }

            return max;
        }

        private void adjustColumnWidths(String[][] rows, int[] widths) {
            String[][] var3 = rows;
            int var4 = rows.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String[] row = var3[var5];
                if (row != null) {
                    for(int c = 0; c < widths.length; ++c) {
                        String cv = this.getCellValue(safeGet(row, c, this.asNull));
                        int l = cv.length();
                        if (widths[c] < l) {
                            widths[c] = l;
                        }
                    }
                }
            }

        }

        private static String padRight(String s, int n) {
            return String.format("%1$-" + n + "s", s);
        }

        private static String safeGet(String[] array, int index, String defaultValue) {
            return index < array.length ? array[index] : defaultValue;
        }

        private String getCellValue(Object value) {
            return value == null ? this.asNull : value.toString();
        }
    }
}
