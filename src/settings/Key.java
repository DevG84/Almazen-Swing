package settings;

public class Key {
    
    String cifrada="";
    char matriz[][]={
        {'A','a','B','g','C','m','D','q','E'},
        {'F','b','G','h','H','n','I','r','J'},
        {'K','c','L','i','M','ñ','N','s','Ñ'},
        {'O','d','P','j','Q','o','R','t','S'},
        {'T','e','U','k','V','p','W','u','X'},
        {'Y','f','Z','l','0','1','2','3','4'},
        {'$','#','+','_','-','.','9','8','7'},
        {'%','*','5','6','x','v','y','w','z'}
    };
    
    public String getPassword(String password){
        //Ciclo que recorre la longitud de la contraseña
        char caracter;
        for(int i=0;i<password.length();i++){
            //System.out.println(password.charAt(i));
            caracter=password.charAt(i);
            for(int x=0;x<8;x++){
             for(int y=0;y<9;y++){
                if(caracter==(char)matriz[x][y]){
                    cifrada=cifrada+x+y;
                    break;
                }
             }
            }
        }
        return cifrada;
    }
}
