/**
 * Copyright 2017 The GreyCat Authors.  All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package greycat.ml;

import greycat.*;
import greycat.struct.DoubleArray;
import greycat.struct.EGraph;
import greycat.struct.ENode;

public class BugTest {

    public static void main(String[] arg) {

        Graph g = GraphBuilder.newBuilder().build();

        g.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                Node hostnode = g.newNode(0, 0);

                //Creating an egraph at time 0, will call it eg0
                EGraph eg0 = (EGraph) hostnode.getOrCreate("egraph", Type.EGRAPH);
                ENode enode0 = eg0.newNode();
                enode0.set("total", Type.INT, 1);
                ((DoubleArray) enode0.getOrCreate("sum", Type.DOUBLE_ARRAY)).initWith(new double[]{0, 0, 0});

                hostnode.travelInTime(1, new Callback<Node>() {
                    @Override
                    public void on(Node hostnode1) {
                        hostnode1.rephase(); //rephase the state
                        EGraph eg1 = (EGraph) hostnode1.getOrCreate("egraph", Type.EGRAPH);
                        ENode enode1 = eg1.root();
                        int total = (int) enode1.get("total");
                        enode1.set("total", Type.INT, total + 1);
                        double[] sum = ((DoubleArray) enode1.get("sum")).extract();
                        sum[0]++;
                        sum[1]++;
                        sum[2]++;
                        ((DoubleArray) enode1.getOrCreate("sum", Type.DOUBLE_ARRAY)).initWith(sum);


                        hostnode.travelInTime(0, new Callback<Node>() {
                            @Override
                            public void on(Node res0) {
                                EGraph egres0 = (EGraph) res0.getOrCreate("egraph", Type.EGRAPH);
                                ENode enoderes0 = egres0.root();
                                int total = (int) enoderes0.get("total");
                                double[] sum = ((DoubleArray) enoderes0.get("sum")).extract();
                                System.out.println("time 0: total: " + total + " sum:[" + sum[0] + "," + sum[1] + "," + sum[2] + "] should be [0,0,0]");


                                hostnode.travelInTime(1, new Callback<Node>() {
                                    @Override
                                    public void on(Node res1) {
                                        EGraph egres1 = (EGraph) res1.getOrCreate("egraph", Type.EGRAPH);
                                        ENode enoderes1 = egres1.root();
                                        int total = (int) enoderes1.get("total");
                                        double[] sum = ((DoubleArray) enoderes1.get("sum")).extract();
                                        System.out.println("time 1: total: " + total + " sum:[" + sum[0] + "," + sum[1] + "," + sum[2] + "] should be [1,1,1]");
                                    }
                                });
                            }


                        });

                    }
                });


            }
        });


    }

}