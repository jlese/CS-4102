import java.util.*;
public class daycareGreedy {


    static class room {
        int start, finish;
        public room(int s, int f) {
            this.start = s;
            this.finish = f;
        }

    }
    
    static class daycare {
        int total_cap, total_kids, trailer;
        ArrayList<room> incr, same, decr;
        public daycare(ArrayList<room> increasing, ArrayList<room> s, ArrayList<room> decreasing, int tr, int ro, int kid) {
            this.incr = increasing;
            this.same = s;
            this.decr = decreasing;
            this.trailer = tr;
            this.total_cap = ro;
            this.total_kids = kid;
        }

        public void sort() {
            //Sort each section of rooms
            Collections.sort(this.incr, new Comparator<room>() {
                @Override
                public int compare(room a, room b) {
                    return Integer.compare(a.start, b.start);
                }
            });

            Collections.sort(this.same, new Comparator<room>() {
                @Override
                public int compare(room a, room b) {
                    return Integer.compare(a.start, b.start);
                }
            });

            Collections.sort(this.decr, new Comparator<room>() {
                @Override
                public int compare(room a, room b) {
                    return Integer.compare(b.finish, a.finish);
                }
            });

            
        }

        public void renovate() {
            //Iterate through every section, executing move kids on them
            for (int i = 0; i < this.incr.size(); i++) {
                this.total_cap -= this.incr.get(i).start;

                if (this.total_cap < this.total_kids) {
                    int diff = this.total_kids - this.total_cap;
                    this.trailer += diff;
                    this.total_cap += this.incr.get(i).start + this.incr.get(i).finish;
                } else {
                    this.total_cap += this.incr.get(i).start;
                }
                //System.out.println("total: " + this.total_cap + " trailer: " + this.trailer + " kids: " + this.total_kids);
            }

            for(int i = 0; i < this.same.size(); i++) {
                this.total_cap -= this.same.get(i).start;

                if (this.total_cap < this.total_kids) {
                    int diff = this.total_kids - this.total_cap;
                    this.trailer += diff;
                    this.total_cap += this.same.get(i).start + this.same.get(i).finish;
                }else {
                    this.total_cap += this.same.get(i).start;
                }
                //System.out.println("total: " + this.total_cap + " trailer: " + this.trailer + " kids: " + this.total_kids);
            }
           
            for(int i = 0; i < this.decr.size(); i++) {
                this.total_cap -= this.decr.get(i).start;

                if (this.total_cap < this.total_kids) {
                    int diff = this.total_kids - this.total_cap;
                    this.trailer += diff;
                    this.total_cap += this.decr.get(i).start + this.decr.get(i).finish;
                }else {
                    this.total_cap += this.decr.get(i).start;
                }
               // System.out.println("total: " + this.total_cap + " trailer: " + this.trailer + " kids: " + this.total_kids);
            }



        }

        public void printSections() {
            System.out.println("Increasing");
            for(int i = 0; i < this.incr.size(); i++) {
                System.out.println(this.incr.get(i).start + " " + this.incr.get(i).finish);
            }
            System.out.println("Same");
            for(int i = 0; i < this.same.size(); i++) {
                System.out.println(this.same.get(i).start + " " + this.same.get(i).finish);
            }
            System.out.println("Decreasing");
            for(int i = 0; i < this.decr.size(); i++) {
                System.out.println(this.decr.get(i).start + " " + this.decr.get(i).finish);
            }
        }
       
    } 

   

    public static int whichList(int a, int b) {
        if(a - b < 0) {
            return 1;

        } else if (a - b == 0) {
            return 2;
        } else {
            return 3;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String[]> input = new ArrayList<String[]>();


        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.isEmpty()) break;
            String[] data = line.split(" ");
            input.add(data);

        }

        
        

        ArrayList<room> increasing = new ArrayList<room>();
        ArrayList<room> same = new ArrayList<room>();
        ArrayList<room> decreasing = new ArrayList<room>();
        int capacity = 0;
        for(int i = 1; i < input.size(); i++) {
            if(input.get(i).length > 1) {
                int s = Integer.parseInt(input.get(i)[0]);
                int f = Integer.parseInt(input.get(i)[1]);
                capacity += s;
                int spot = whichList(s, f);
                room currR = new room(s, f);
                if (spot == 1) {
                    increasing.add(currR);
                } else if (spot == 2) {
                    same.add(currR);
                } else {
                    decreasing.add(currR);
                }

            } 
            
            if(i + 1 == input.size() || input.get(i).length <2) {
                daycare currDC = new daycare(increasing, same, decreasing, 0, capacity, capacity);

                currDC.sort();
                //currDC.printSections();
                currDC.renovate();
                System.out.println(currDC.trailer);
               
                
                capacity = 0;
                increasing.clear();
                same.clear();
                decreasing.clear();
            
        }

        
        /*
        ArrayList<room> increasing = new ArrayList<room>();
        ArrayList<room> same = new ArrayList<room>();
        ArrayList<room> decreasing = new ArrayList<room>();
        ArrayList<daycare> daycares = new ArrayList<daycare>();
        
        scanner.nextLine();
        int capacity = 0;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.isEmpty()) break;
            String[] data = line.split(" ");

            if (data.length > 1) {
                int s = Integer.parseInt(data[0]);
                int f = Integer.parseInt(data[1]);
                capacity+= s;
                int spot = whichList(s, f);
                room currR = new room(s, f);
                if (spot == 1) {
                    increasing.add(currR);
                } else if (spot == 2) {
                    same.add(currR);
                } else {
                    decreasing.add(currR);
                }
            } else {
                daycare currDC = new daycare(increasing, same, decreasing, 0, capacity, capacity);
                daycare temp = currDC;
                daycares.add(currDC);
                
                currDC = null;
                capacity = 0;
                increasing.clear();
                same.clear();
                decreasing.clear();
            }
            
            
        }
        for(int i = 0; i < daycares.size(); i++) {
            daycares.get(i).sort();
            daycares.get(i).renovate();
            System.out.println(daycares.get(i).trailer);
        }
        */
        /*
        for(int i = 0; i < currList.size(); i++) {
            System.out.println(currList.get(i).start + " " + currList.get(i).finish);
        }
        */
        scanner.close();

        
    }
}
}