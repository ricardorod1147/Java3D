/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graficosjava3d;

/**
 *
 * @author Ricardo Rodríguez
 */
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.io.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Chaircalifornia extends Applet{
      private static final long serialVersionUID = 1L;
    
    public static void main(String[] args){
        if (args.length>=1)
            new MainFrame(new Chaircalifornia(args[0]),850,600);
        else
            new MainFrame(new Chaircalifornia(null),850,600);
    }
    
    public Chaircalifornia (String filename){
        //se crea el universo
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D c = new Canvas3D(config);
        add("Center",c);
        
        //se crea el metodo de la escena y se une al universo
        BranchGroup escena = crearescenaG(filename);
        SimpleUniverse u = new SimpleUniverse(c);
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(escena);
    }
    BoundingBox limites = new BoundingBox(new Point3d(-100,-100,-100),new Point3d(400,400,400));
    
    public BranchGroup crearescenaG(String filename){
        BranchGroup escenaRoot = new BranchGroup();
        escenaRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        escenaRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        escenaRoot.addChild(dibujarfondo());
        
        //ambiente de luces
        Color3f luz1Color = new Color3f(1.0f,1.0f,0.9f);
        Vector3f luz1Direccion = new Vector3f(4.0f,-7.0f,-12.0f);
        Color3f luz2Color = new Color3f(0.3f,0.3f,0.4f);
        Vector3f luz2Direccion = new Vector3f(-6.0f,-2.0f,-1.0f);
        Color3f ambienteColor = new Color3f(0.1f,0.1f,0.1f);
        
        AmbientLight Nodoambienteluces = new AmbientLight(ambienteColor);
        Nodoambienteluces.setInfluencingBounds(limites);
        escenaRoot.addChild(Nodoambienteluces);
        
        //direccion de las luces
        DirectionalLight luz1 = new DirectionalLight(luz1Color,luz1Direccion);
        luz1.setInfluencingBounds(limites);
        escenaRoot.addChild(luz1);
        
        DirectionalLight luz2 = new DirectionalLight(luz2Color,luz2Direccion);
        luz2.setInfluencingBounds(limites);
        escenaRoot.addChild(luz2);
        
        TransformGroup escenaTG = new TransformGroup();
        escenaTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        escenaRoot.addChild(escenaTG);
        
        Alpha rotacionAlpha = new Alpha(-1,Alpha.INCREASING_ENABLE,0,0,4000,0,0,0,0,0);
        
        Transform3D yAxis = new Transform3D();
        RotationInterpolator rotar = new RotationInterpolator(rotacionAlpha,escenaTG,yAxis,0.0f,(float) Math.PI*2.0f);
        rotar.setSchedulingBounds(limites);
        escenaTG.addChild(rotar);
        
        //cargar objeto
        TransformGroup objTG = new TransformGroup();
        Transform3D objTrans = new Transform3D();
        objTG.getTransform(objTrans);
        objTrans.setTranslation(new Vector3d(0.0,0.0,0.0));
        objTrans.setScale(0.4);
        objTG.setTransform(objTrans);
        
        escenaTG.addChild(objTG);
        
        int flags = ObjectFile.RESIZE;
        ObjectFile file = new ObjectFile(flags,(float)(49.0*Math.PI/180.0));
        
         Scene s = null;
        
        try{
           s = file.load(filename == null ? "src\\images\\Californiachair.obj" : filename);
           //objTG.addChild(s.getSceneGroup());
        }
        catch (FileNotFoundException | ParsingErrorException | IncorrectFormatException e){
            System.err.println(e);
            System.exit(1);
        }
        
        objTG.addChild(s.getSceneGroup());
        return escenaRoot;
    }
    
    private TransformGroup dibujarfondo(){
        TransformGroup fondo = new TransformGroup();
        TextureLoader espacio = new TextureLoader("src/images/chaircalifornia.jpg",this);
        Background back = new Background();
        back.setImage(espacio.getImage());
        back.setImageScaleMode(Background.SCALE_FIT_ALL);
        back.setApplicationBounds(limites);
        fondo.addChild(back);
        
        return fondo;
    }
    
}
