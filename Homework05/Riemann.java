public class Riemann {

  private static final double DEFAULT_PERCENT_VALUE = .01;
  private static double minPercentValue = 0.0;
  private static String[] globalArgs;
  private static double[] bounds = new double[2];
  private static double[] coeffs;


  public static void determinePercent(String globalArgs[]){
    if (globalArgs[globalArgs.length-1].contains("%")){
      minPercentValue = Double.parseDouble(globalArgs[globalArgs.length-1].substring(0, (globalArgs[globalArgs.length-1].length()-1)));
    } else {
      minPercentValue = DEFAULT_PERCENT_VALUE;
    }
  }

  public static void validateArgs(String globalArgs[]) throws IllegalArgumentException{
    int argsLength = globalArgs.length;
    if (globalArgs[argsLength - 1].contains("%") == true){
      argsLength -= 1;
    }
    if (globalArgs[0].equals("sin") || globalArgs[0].equals("cos") || globalArgs[0].equals("tan")){
      coeffs = new double[2];
      coeffs[0] = 0;
      coeffs[1] = 1;
    } else {
      try {
        coeffs = new double[argsLength - 3];
      } catch (NegativeArraySizeException NASE){
        System.out.println("Incorrect number of coefficents");
      }
    }
    bounds[0] = Double.parseDouble(globalArgs[argsLength - 2]);
    bounds[1] = Double.parseDouble(globalArgs[argsLength - 1]);
    if(bounds[1] <= bounds[0]){
      System.out.println("Cannot have the second bound smaller than the first bound");
      System.exit(0);
    }
    for (int i = 1; i <= argsLength - 3; i++){
      coeffs[i-1] = Double.parseDouble(globalArgs[i]);
      System.out.println("Coefficient " + (i-1) + " is: " + coeffs[i-1]);
    }
  }

  public static double poly(int n){
    double midValue = (bounds[0] +(((bounds[1] - bounds[0])/n)/2));
    double width = ((bounds[1] - bounds[0])/n);
    //System.out.println("Width: " + width);
    double height = 0;
    double totalArea = 0;
    for (int i = 0; i < n; i++){
      for (int j = 0; j < coeffs.length; j++){
        height += coeffs[j] * Math.pow(midValue,j);
      }
      totalArea += (width * height);
      midValue += width;
      height = 0;
    }
    return totalArea;
  }

  public static double sin(int n){
    double midValue = (bounds[0] +(((bounds[1] - bounds[0])/n)/2));
    double width = ((bounds[1] - bounds[0])/n);
    //System.out.println("Width: " + width);
    double height = 0;
    double totalArea = 0;
    for (int i = 0; i < n; i++){
      height = Math.sin((coeffs[1] * midValue) + coeffs[0]);
      totalArea += (width * height);
      midValue += width;
    }
    return totalArea;
  }

  public static double cos(int n){
    double midValue = (bounds[0] +(((bounds[1] - bounds[0])/n)/2));
    double width = ((bounds[1] - bounds[0])/n);
    //System.out.println("Width: " + width);
    double height = 0;
    double totalArea = 0;
    for (int i = 0; i < n; i++){
      height = Math.cos((coeffs[1] * midValue) + coeffs[0]);
      totalArea += (width * height);
      midValue += width;
    }
    return totalArea;
  }

  public static double tan(int n){
    double midValue = (bounds[0] +(((bounds[1] - bounds[0])/n)/2));
    double width = ((bounds[1] - bounds[0])/n);
    //System.out.println("Width: " + width);
    double height = 0;
    double totalArea = 0;
    for (int i = 0; i < n; i++){
      height = Math.tan((coeffs[1] * midValue) + coeffs[0]);
      totalArea += (width * height);
      midValue += width;
    }
    return totalArea;
  }

  public static void testValidateArgs(){
    String[] testArgs = {"poly","00","8.0","-2.0","1.0","4.0","1e-6%"};
    validateArgs(testArgs);
  }

  public static void runMyTests(){
    testValidateArgs();
    //testCalculatePoly();
    //testCalculateSine();
  }

  public static void main(String args[]){
    globalArgs = args;
    double previousTotalArea;
    double currentTotalArea;
    int n = 1;
    switch (globalArgs[0]){
      case "runtests": runMyTests();
      case "poly":
      try {
        determinePercent(globalArgs);
        validateArgs(globalArgs);
        System.out.println("Bounds: " + bounds[0] + ", " + bounds[1] +"\n");
      } catch (NumberFormatException nfe) {
        System.out.println("Please enter a valid argument");
        System.exit(0);
      } catch (ArrayIndexOutOfBoundsException aioobe){
        System.out.println("Not enough arguments");
        System.exit(0);
      }
            while (true) {
                previousTotalArea = poly(n);
                currentTotalArea = poly(n + 1);
                //System.out.println("Previous Area: "+ previousTotalArea);
                //System.out.println("Current Area: " + currentTotalArea);
                double percentValue = Math.abs(1 - (currentTotalArea/previousTotalArea));
                if (Double.isNaN(percentValue)){
                  break;
                }
                //System.out.println("Percent Value is: " + percentValue + "% at iteration " + n);
                if (percentValue < minPercentValue){
                  System.out.println("Number of rectangles used: " + (n+1));
                  System.out.println("Area under the curve is: " + currentTotalArea);
                  break;
                }
                previousTotalArea = currentTotalArea;
                n +=2;
              }
              break;
      case "sin" :
      try {
        determinePercent(globalArgs);
        validateArgs(globalArgs);
        System.out.println("Bounds: " + bounds[0] + ", " + bounds[1] +"\n");
      } catch (NumberFormatException nfe) {
        System.out.println("Please enter a valid argument");
        System.exit(0);
      } catch (ArrayIndexOutOfBoundsException aioobe){
        System.out.println("Not enough arguments");
        System.exit(0);
      }
          while (true) {
              previousTotalArea = sin(n);
              currentTotalArea = sin(n + 1);
              double percentValue = Math.abs(1 - (currentTotalArea/previousTotalArea));
              if (Double.isNaN(percentValue)){
                break;
              }
              //System.out.println("Percent Value is: " + percentValue + "% at iteration " + n);
              if (percentValue < minPercentValue){
                System.out.println("Number of rectangles used: " + n);
                System.out.println("Area under the curve is: " + currentTotalArea);
                break;
              }
              previousTotalArea = currentTotalArea;
              n += 2;
            }
            break;

      case "cos" :
      try {
        determinePercent(globalArgs);
        validateArgs(globalArgs);
        System.out.println("Bounds: " + bounds[0] + ", " + bounds[1] +"\n");
      } catch (NumberFormatException nfe) {
        System.out.println("Please enter a valid argument");
        System.exit(0);
      } catch (ArrayIndexOutOfBoundsException aioobe){
        System.out.println("Not enough arguments");
        System.exit(0);
      }
          while (true) {
              previousTotalArea = cos(n);
              currentTotalArea = cos(n + 1);
              double percentValue = Math.abs(1 - (currentTotalArea/previousTotalArea));
              if (Double.isNaN(percentValue)){
                break;
              }
              //System.out.println("Percent Value is: " + percentValue + "% at iteration " + n);
              if (percentValue < minPercentValue){
                System.out.println("Number of rectangles used: " + n);
                System.out.println("Area under the curve is: " + currentTotalArea);
                break;
              }
              previousTotalArea = currentTotalArea;
              n += 2;
            }
            break;

      case "tan" :
      try {
        determinePercent(globalArgs);
        validateArgs(globalArgs);
        System.out.println("Bounds: " + bounds[0] + ", " + bounds[1] +"\n");
      } catch (NumberFormatException nfe) {
        System.out.println("Please enter a valid argument");
        System.exit(0);
      } catch (ArrayIndexOutOfBoundsException aioobe){
        System.out.println("Not enough arguments");
        System.exit(0);
      }
          while (true) {
              previousTotalArea = tan(n);
              currentTotalArea = tan(n + 1);
              double percentValue = Math.abs(1 - (currentTotalArea/previousTotalArea));
              if (Double.isNaN(percentValue)){
                break;
              }
              //System.out.println("Percent Value is: " + percentValue + "% at iteration " + n);
              if (percentValue < minPercentValue){
                System.out.println("Number of rectangles used: " + n);
                System.out.println("Area under the curve is: " + currentTotalArea);
                break;
              }
              previousTotalArea = currentTotalArea;
              n += 2;
            }
            break;
      default: System.out.println("Please enter a valid function type. Possible types include: poly, sin, cos, tan");
    }

  }

}
//cd OneDrive\Computer Science 186\Homework05
//javac Riemann.java && java Riemann poly 0 8 -2 -3 4 1e-6%
