package fingerprint;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.sun.java.swing.plaf.windows.resources.windows;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyValue;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;

          
/**
 *
 * @author danml
 */




public class MenusController implements Initializable {

    @FXML
    public AnchorPane rootPane;
    @FXML
    private StackPane fabsContainer;
    @FXML
    private AnchorPane paneUsers;
    @FXML
    private AnchorPane paneTickets;
    @FXML
    private AnchorPane paneBuses;
    @FXML
    private AnchorPane paneDrivers;
    @FXML
    private HBox boxMenus;

    @FXML
    private Label labelstate2;
    @FXML
   private Label  labelA;

     @FXML
   private Label  labelB;
      @FXML
   private Label  labelC;
       @FXML
   private Label  labelD;
       @FXML
       private Label labelstate;

       @FXML
    private JFXButton btnEdit;
       @FXML
    private ImageView imageA;
     @FXML
    private ImageView imageB;
      @FXML
    private ImageView imageD;
      @FXML
    private ImageView imageC;
    @FXML
    private ImageView image0;
     @FXML
    private ImageView image1;
      @FXML
    private ImageView image2;
       @FXML
    private ImageView image3;
        @FXML
    private ImageView image4;
     @FXML
    private ImageView image5;
      @FXML
    private ImageView image6;
       @FXML
    private ImageView image7;
        @FXML
    private ImageView image8;
   
       static Image imag;
       @FXML
     private ImageView imageTrashA;
         @FXML
     private ImageView imageTrashB;
           @FXML
     private ImageView imageTrashC;
             @FXML
     private ImageView imageTrashD;
    @FXML
    private Circle circ;
    
   @FXML
    private JFXButton btnload;
   @FXML
    private JFXButton btnsearch;
   @FXML
    private JFXTextField txtName;
   @FXML
    private Label labfound;
   @FXML
    private Label labSearch;
   public int x;
   public int y;
class node
{
//    public ArrayList<Integer> neighbooringCellIndices=new ArrayList<Integer>();
    public int X,Y,Theta,Type;
    public node(int X,int Y,int Type)
    {
        this.X=X;
        this.Y=Y;
        this.Type=Type;
    }
}
//public class FR
//{

  
    public  int[][] binarizing(BufferedImage img) throws Exception
    {
        int i,j,avg=0;
        int img_bin[][]=new int[img.getWidth()][img.getHeight()];
        BufferedImage greyscale=new BufferedImage(img.getWidth(),img.getHeight(),img.getType());
        Color c;
        for(i=0;i<img.getWidth();i++)
            for(j=0;j<img.getHeight();j++)
            {
                c=new Color(img.getRGB(i, j));
                
                avg=c.getRed()+c.getGreen()+c.getBlue();
                avg/=3;
                if(avg>120)
                    img_bin[i][j]=1;
                else
                    img_bin[i][j]=0;
                
                greyscale.setRGB(i, j, new Color(avg,avg,avg).getRGB() );
            }
        ImageIO.write(greyscale, "png", new File("greyscale.png"));
        return img_bin;
    }
    public  int[][] getFastParallelAlgoSkeleton(int img_bin[][]) throws Exception
    {
        int i,j,k,A,B;
        //clockwise
        int di[]=new int[]{0,0, -1,-1,0,1,1,1,0,-1};//1st 2 elements are useless
        int dj[]=new int[]{0,0, 0,1,1,1,0,-1,-1,-1};
        boolean EVEN=true;
        
        BufferedImage debugImg=new BufferedImage(img_bin.length, img_bin[0].length, BufferedImage.TYPE_INT_ARGB);
        
        int skeleton[][]=new int[img_bin.length][img_bin[0].length];
        for(i=1;i<img_bin.length-1;i++)
            for(j=1;j<img_bin[i].length-1;j++)
            {
                skeleton[i][j]=0;
                A=img_bin[i][j];
                B=0;
                for(k=2;k<=9;k++)// from P2+P3+...+P9
                    B+=img_bin[i+di[k]][j+dj[k]];
                
                // A=1  AND 3<= B <=6?
                if(A==1 && 3<=B && B<=6)
                {
                    if(     !EVEN
                            &&
                            img_bin[i+di[2]][j+dj[2]]*
                            img_bin[i+di[4]][j+dj[4]]*
                            img_bin[i+di[6]][j+dj[6]] == 0
                            &&
                            img_bin[i+di[4]][j+dj[4]]*
                            img_bin[i+di[6]][j+dj[6]]*
                            img_bin[i+di[8]][j+dj[8]] == 0)
                        
                                skeleton[i][j]=1;
                    
                    
                    if(     EVEN
                            &&
                            img_bin[i+di[2]][j+dj[2]]*
                            img_bin[i+di[4]][j+dj[4]]*
                            img_bin[i+di[8]][j+dj[8]] == 0
                            &&
                            img_bin[i+di[2]][j+dj[2]]*
                            img_bin[i+di[6]][j+dj[6]]*
                            img_bin[i+di[8]][j+dj[8]] == 0)
                        
                                skeleton[i][j]=1;
                    
                        
                }
                
                if(skeleton[i][j]==0)
                        debugImg.setRGB(i, j, new Color(255, 255, 255).getRGB());
                else
                        debugImg.setRGB(i, j, new Color(0, 0, 0).getRGB());
                
                EVEN=!EVEN;
            }
        
        ImageIO.write(debugImg, "png", new File("thin.png"));
        return skeleton;
    }
    
    public  int[][] getCN(int skeleton[][])//after thinning
    {
        int di[]=new int[]{0,-1,-1,-1,0,1,1,1};
        int dj[]=new int[]{1,1,0,-1,-1,-1,0,1};
        
        int i,j,k;
        
        
        int trimL=skeleton[0].length-1;
        int trimT=skeleton.length-1;
        for(i=0;i<skeleton.length;i++)
            for(j=0;j<skeleton[0].length;j++)
                if(skeleton[i][j]>0)
                {
                    trimT=Math.min(trimT, i);
                    trimL=Math.min(trimL, j);
                }
        
        int CN[][]=new int[skeleton.length-trimT][skeleton[0].length-trimL];
        
        for(i=trimT;i<skeleton.length-1;i++)
            for(j=trimL;j<skeleton[0].length-1;j++)
            {
                CN[i-trimT][j-trimL]=0;
                for(k=1;k<8;k++)
                    CN[i-trimT][j-trimL]+=Math.abs(skeleton[i+di[k-1]][j+dj[k-1]]-skeleton[i+di[k]][j+dj[k]]);
            }
        return CN;
    }
    
    public  node[] genGraph(int CN[][])
    {
        ArrayList<node> graph=new ArrayList<node>();
        int i,j,k,currentIndex=-1;
        for(i=0;i<CN.length;i++)
            for(j=0;j<CN[0].length;j++)
                if(CN[i][j]>0)
                {
//                    currentIndex= graph.size()-1;
//                    if(graph.size()==0)
//                        currentIndex=0;
                    
                    graph.add(new node(i, j, CN[i][j]));
                    //TODO: Heap out of memory fix
//                    for(k=0;k<CN.length || k<CN[i].length;k++) // K is the distance between center point & neighbooring cell(s)
//                    {
//                        if(i+k<CN.length
//                                && CN[i+k][j]>0)
//                        {
//                            graph.get(currentIndex).neighbooringCellIndices.add(graph.size());
//                            graph.add(new node(i+k, j, CN[i+k][j]));
//                        }
//                        if(j+k<CN[0].length
//                                && CN[i][j+k]>0)
//                        {
//                            graph.get(currentIndex).neighbooringCellIndices.add(graph.size());
//                            graph.add(new node(i, j+k, CN[i][j+k]));
//                        }
//                        
//                    }
                    
                    
                }
        
        
        node    GraphNodes[]=new node[graph.size()];
        graph.toArray(GraphNodes);
        return GraphNodes;
    }
    
    public  int compare(node[] graph1,node[] graph2)
    {
        
        int MinimumDistance=Integer.MAX_VALUE,missing=0;
        
        Queue<Integer> Q=new LinkedList<Integer>();
        int i,j,currentBest,currentCost,currentNode1=0,currentNode2=0,TotalDifference=0;
        
        boolean used[]=new boolean[graph2.length];
        Arrays.fill(used, false);
        
        
        currentNode1=0;
        TotalDifference=0;
        
        Q.add(currentNode1);
        Q.add(TotalDifference);
        
        
        while(!Q.isEmpty())
        {
            currentNode1=Q.remove();
            TotalDifference=Q.remove();
            
            currentCost=1<<30;
            currentBest=-1;
            
            if(     currentNode1==graph1.length-1)
            {
                MinimumDistance=Math.min(MinimumDistance,TotalDifference);
                break;
            }
            
            
            for(i=0;i<graph2.length;i++)
                if(graph1[currentNode1].Type== graph2[i].Type &&
                        
                        used[i]==false &&
                        
                    (Math.abs(graph1[currentNode1].X-graph2[i].X)+
                     Math.abs(graph1[currentNode1].Y-graph2[i].Y))<=currentCost
                  )
                {
                    
                    
                  //TODO: branching should be here, but returns Heap out of memory,so, is picking the closest 1 w same type fine?!
                    
                  currentCost=(Math.abs(graph1[currentNode1].X-graph2[i].X)+
                                Math.abs(graph1[currentNode1].Y-graph2[i].Y));
                  
                  currentBest=i;  
                  
                  
                }
            
            
            if(currentBest>-1)
            {
                used[currentBest]=true;
                
                Q.add(currentNode1+1);
                Q.add(TotalDifference+currentCost);
            }
            else
            {
                missing++;
                Q.add(currentNode1+1);
                Q.add(TotalDifference);
            }
            
        }
        System.out.println("Skipped="+missing);
        return MinimumDistance;
    }
//}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // System.out.println(LoginController.privilage);

      /*  int[] puzzle=
        {
            8,7,4,
            5,3,6,
            0,2,1  
           // 0,2,4,
            //3,6,5,
            //7,1,8      
        };
        ImageView  []x=new ImageView[9] ;
               x[0]=image0;
               x[1]=image1;
               x[2]=image2;
               x[3]=image3;
               x[4]=image4;
               x[5]=image5;
               x[6]=image6;
               x[7]=image7;
               x[8]=image8;
        for(int i=0;i<puzzle.length;i++)
        {
               
                     if(puzzle[i]==0)
                     {
                         continue;
                     }
                   
                        
                            imag = new Image("/icons/"+puzzle[i]+".png",137,135,false,true);
                            x[i].setImage(imag);
                
                  
                }
        
                          
                         */
        }
        //DbHandler handler = new DbHandler();
             /*  Image imag1 = new Image("/icons/1.png",137,135,false,true);
               Image imag2 = new Image("/icons/2.png",137,135,false,true);
               Image imag3 = new Image("/icons/3.png",137,135,false,true);
               Image imag4 = new Image("/icons/4.png",137,135,false,true);
               Image imag5 = new Image("/icons/5.png",137,135,false,true);
               Image imag6 = new Image("/icons/6.png",137,135,false,true);
               Image imag7 = new Image("/icons/7.png",137,135,false,true);
               Image imag8 = new Image("/icons/8.png",137,135,false,true);
               
               
               
                image0.setImage(imag1);
                image1.setImage(imag2);
                image2.setImage(imag3);
                image3.setImage(imag4);
                image4.setImage(imag5);
                image5.setImage(imag6);
                image6.setImage(imag7);
                image7.setImage(imag8);
               */
                

       // setUpFabs();
        //setUpRipples();
            //   Environment en=new Environment();
       
      /* Environment en=new Environment();
       Agent agent=new Agent();
        en.init(agent);
       
        try {
            en.run();
        } catch (InterruptedException ex) {
            Logger.getLogger(MenusController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenusController.class.getName()).log(Level.SEVERE, null, ex);
        }
       */
                //Image image=new Image("/icons/right.png");
                  //     imageA.setImage(image);
    

   

    @FXML
    private void switchToUsers(MouseEvent event) {
      
    }


    @FXML
    private void switchToTickets(MouseEvent event) {
  
    }
     @FXML
    private void editDriver(ActionEvent event) throws InterruptedException, Exception  {
     
        labfound.setText("");
        labSearch.setText("");
        labSearch.setText("جاري البحث ");
            //int i = 0;
        node[] graph1=
             genGraph( 
                    getCN(
                        getFastParallelAlgoSkeleton(
                            binarizing(
                                ImageIO.read(new File("samples/"+txtName.getText()+".png"))))));
       
         Task task = new Task() {
    @Override
    protected Object call() throws Exception {

        
                 
        for(int i=1;i<=6;i++)
        {
            System.out.println("Comparing Sample#"+i+":"+
                 compare(graph1, 
                    genGraph( 
                        getCN(
                            getFastParallelAlgoSkeleton(
                                binarizing(
                                    ImageIO.read(new File("samples/"+txtName.getText()+".png")))))) )
        );
            imag = new Image("/icons/"+i+".png",202,199,false,true);
                            image2.setImage(imag);
                           // System.out.println(txtName.getText());
            
            if(compare(graph1, 
                    genGraph( 
                        getCN(
                            getFastParallelAlgoSkeleton(
                                binarizing(
                                    ImageIO.read(new File("samples/"+i+".png")))))) )==0)
            {
                x=i;
                  Platform.runLater(new Runnable()

                {

                    @Override

                    public void run()

                    {
                    // System.out.println("this image match "+i+".png");
                 labfound.setText("matching with fingerprint in database with id ="+x);
                //labfound.setText("found in database id="+i);
                labSearch.setText("تطابق مع");

                    }

                });
                
                
                break;
            }
            
           // Thread.sleep(200);
        }
        //labfound.setText("Not found in database");
        //labSearch.setText("");
        return null;
                           }
};
         
              Thread t = new Thread(task);

                        t.start();
                        
        
       // else
        //    labfound.setText("Not found in database");
            
    }
       
       
                 
      
        
    @FXML
    private void switchToBuses(MouseEvent event) {
   
    }

    @FXML
    private void switchToDrivers(MouseEvent event) {
   

    }

    private void setUpFabs() {
        //Setting up icons for button
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE_ALT_NOTCH);
        icon.setStyle("-fx-fill:#ffffff;-fx-size:13px;");

        FontAwesomeIconView closeicon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
        closeicon.setStyle("-fx-fill:#ffffff;-fx-size:13px;");
        FontAwesomeIconView logicon = new FontAwesomeIconView(FontAwesomeIcon.UNLOCK_ALT);
        logicon.setStyle("-fx-fill:#ffffff;-fx-size:13px;");

        JFXButton button1 = new JFXButton();
        Label label1 = new Label("G1");
        button1.setGraphic(icon);
        label1.setStyle("-fx-text-fill:WHITE");
        button1.setButtonType(ButtonType.RAISED);
        button1.setStyle("-fx-pref-width:30px;-fx-background-color:#F6C574;"
                + "-fx-background-radius:30px;-fx-pref-height:30px;");

        JFXButton button2 = new JFXButton();
        button2.setTooltip(new Tooltip("Log off"));
        button2.setButtonType(ButtonType.RAISED);
        button2.setGraphic(logicon);
        button2.setStyle("-fx-pref-width:30px;-fx-background-color:#F6C574;"
                + "-fx-background-radius:30px;-fx-pref-height:30px;");
        button2.setOnAction((ActionEvent event) -> {
            try {
                paneDrivers.getScene().getWindow().hide();
                Parent rood = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene scene = new Scene(rood);
                Stage driverStage = new Stage();
                driverStage.setScene(scene);
                driverStage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenusController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JFXButton button3 = new JFXButton();
        button3.setButtonType(ButtonType.RAISED);
        button3.setTooltip(new Tooltip("Exit"));
        button3.setGraphic(closeicon);
        button3.setStyle("-fx-pref-width:30px;-fx-background-color:#F87951;"
                + "-fx-background-radius:30px;-fx-pref-height:30px;");
        button3.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });

        JFXNodesList nodesList = new JFXNodesList();
        nodesList.setSpacing(10);
        nodesList.addAnimatedNode(button1, (expanded) -> {
            return new ArrayList<KeyValue>() {
                {
                    add(new KeyValue(button1.rotateProperty(), expanded ? 360 : 0, Interpolator.EASE_BOTH));
                }
            };
        });
        nodesList.addAnimatedNode(button2);
        nodesList.addAnimatedNode(button3);

        fabsContainer.getChildren().add(nodesList);

    }
     @FXML
    private void Load(ActionEvent event) throws InterruptedException  {
       // ImageView  image0=new ImageView() ;
         imag = new Image("/icons/"+txtName.getText()+".png",202,199,false,true);
                            image0.setImage(imag);
                            System.out.println(txtName.getText());
    }

    private void setUpRipples() {
        JFXRippler ripplerUser = new JFXRippler(paneUsers, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler ripplerDriver = new JFXRippler(paneDrivers, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler ripplerBuses = new JFXRippler(paneBuses, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);
        JFXRippler ripplerTickets = new JFXRippler(paneTickets, JFXRippler.RipplerMask.RECT, JFXRippler.RipplerPos.FRONT);

        boxMenus.getChildren().addAll(ripplerUser, ripplerDriver, ripplerBuses, ripplerTickets);
    }

}
