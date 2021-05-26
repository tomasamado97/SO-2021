#!/bin/bash
menu(){
   echo "1)Opción 1. Probar Fortaleza."
   echo "2)Opción 2. Obtener "o"."
   echo "3)Opción 3. Guardar Informe."
   echo "4)Opción 4. Salir."
   read -p "Ingrese una opción: " opc
   
   touch log_script.txt
   
   while [ $opc -ge 5 -o $opc -le 0 ]
   do
        echo $opc >> log_script.txt
        echo "No es una opción válida"
        read -p "Ingrese una opción: " opc
   done

   echo $opc >> log_script.txt
   
   if [[ $opc == 1 ]]
   then
   opcion1
   fi

   if [[ $opc == 2 ]]
   then
   opcion2
   fi

   if [[ $opc == 3 ]]
   then
   opcion3
   fi

   if [[ $opc == 4 ]]
   then
   exit
   fi
}
opcion1(){
    read -p "Ingrese contrasena para probar su fortaleza: " pass
    echo $pass >> log_script.txt

    #VERIFICO QUE TENGA MAS DE TRES CARACTERES.
    masdetres=true
    if [[ ${#pass} < 4 ]]
    then
      echo "La contrasena es demasiado corta."
      masdetres=false
    fi

    #VERIFICO CARACTERES NUMÉRICOS.
    if [ "$masdetres" = true ]
    then
      contador=0
      termina=9
      passSinNum=$pass
      tieneNumeros=false

      while [ $termina -ge $contador ]
      do 
          buscar=$contador
          var=`echo $passSinNum | grep $buscar | wc -l`
          let contador=$contador+1
          
          if [ $var -ge 1 ]
          then
            tieneNumeros=true
          fi
      done
      
      if [ "$tieneNumeros" = true ]
      then
          echo "Pass OK, tiene numeros."
      else
          echo "Debe contener números."
      fi
    else
      echo "Ingresa una pass nuevamente con más de 3 caracteres. "
    fi

    #VERIFICO LUGARES CONOCIDOS

    if [ "$tieneNumeros" = true ]
    then
      EstaEnDicc=false
      passSinNum=$pass
      
      passSinNum=$(sed 's/0//g' <<< $passSinNum )
      passSinNum=$(sed 's/1//g' <<< $passSinNum )
      passSinNum=$(sed 's/2//g' <<< $passSinNum )
      passSinNum=$(sed 's/3//g' <<< $passSinNum )
      passSinNum=$(sed 's/4//g' <<< $passSinNum )
      passSinNum=$(sed 's/5//g' <<< $passSinNum )
      passSinNum=$(sed 's/6//g' <<< $passSinNum )
      passSinNum=$(sed 's/7//g' <<< $passSinNum )
      passSinNum=$(sed 's/8//g' <<< $passSinNum )
      passSinNum=$(sed 's/9//g' <<< $passSinNum )
    
      while IFS= read -r line
      do 
        if [[ "$line" == *"$passSinNum"* ]]
        then
          EstaEnDicc=true
        fi
      done < diccionario.txt
    
      if [ "$EstaEnDicc" = false ]
      then 
          echo "La contraseña no está en el diccionario, bien :)"
          echo "Se cumplen las condiciones de fortaleza de la contraseña."
      else
          echo "Ingresa una contraseña que, sin numeros, no esté en el diccionario."
      fi
    fi
    menu
}
opcion2(){
    #FALTA VER EL TEMA DE LOS TILDES Y LETRAS CONSONANTES SOLAS.
    
    while IFS= read -r line
    do 
      if [[ "$line" != *"a"* && "$line" != *"e"* && "$line" != *"i"* && "$line" != *"u"* && ${#line} > 1 && "$line" == *"o"* ]]
      then
        echo "$line"
      fi
    done < diccionario.txt
    menu
}
opcion3(){
    DIA=`date +"%d/%m/%Y"`
    HORA=`date +"%H:%M"`

    if [ ! -d informes ]
    then
        echo "Paso a crear la carpeta :)"
        mkdir informes
        touch informes/letras_o.txt
    fi

    cat /dev/null > informes/letras_o.txt

    echo "Se genero el $DIA y a la hora $HORA" >> informes/letras_o.txt

    while IFS= read -r line
    do 
      if [[ "$line" != *"a"* && "$line" != *"e"* && "$line" != *"i"* && "$line" != *"u"* && ${#line} > 1 && "$line" == *"o"* ]]; then
        echo "$line" >> informes/letras_o.txt
      fi
    done < diccionario.txt
    menu
}
#CADA VEZ QUE SE EJECUTA EL BASH, SE LIMPIA EL LOG.
cat /dev/null > log_script.txt
echo 'El usuario ingresó: ' >> log_script.txt
menu
