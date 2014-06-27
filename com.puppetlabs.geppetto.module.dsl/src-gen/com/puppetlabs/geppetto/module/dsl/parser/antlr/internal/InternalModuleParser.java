package com.puppetlabs.geppetto.module.dsl.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import com.puppetlabs.geppetto.module.dsl.services.ModuleGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalModuleParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_DOUBLE", "RULE_LONG", "RULE_DIGIT", "RULE_EXPONENT", "RULE_WS", "'{'", "','", "'}'", "'\"author\"'", "':'", "'\"dependencies\"'", "'\"issues_url\"'", "'\"license\"'", "'\"name\"'", "'\"operatingsystem_support\"'", "'\"project_page\"'", "'\"requirements\"'", "'\"source\"'", "'\"summary\"'", "'\"tags\"'", "'\"version\"'", "'['", "']'", "'\"version_requirement\"'", "'\"operatingsystem\"'", "'\"operatingsystemrelease\"'", "'\"parameters\"'", "'true'", "'false'", "'null'"
    };
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int RULE_LONG=6;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int EOF=-1;
    public static final int RULE_DOUBLE=5;
    public static final int T__30=30;
    public static final int T__19=19;
    public static final int T__31=31;
    public static final int RULE_STRING=4;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__16=16;
    public static final int T__34=34;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int RULE_EXPONENT=8;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__10=10;
    public static final int RULE_WS=9;
    public static final int RULE_DIGIT=7;

    // delegates
    // delegators


        public InternalModuleParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalModuleParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalModuleParser.tokenNames; }
    public String getGrammarFileName() { return "../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g"; }



     	private ModuleGrammarAccess grammarAccess;
     	
        public InternalModuleParser(TokenStream input, ModuleGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Model";	
       	}
       	
       	@Override
       	protected ModuleGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleModel"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:67:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:68:2: (iv_ruleModel= ruleModel EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:69:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_ruleModel_in_entryRuleModel75);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModel85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:76:1: ruleModel returns [EObject current=null] : this_JsonMetadata_0= ruleJsonMetadata ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject this_JsonMetadata_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:79:28: (this_JsonMetadata_0= ruleJsonMetadata )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:81:5: this_JsonMetadata_0= ruleJsonMetadata
            {
             
                    newCompositeNode(grammarAccess.getModelAccess().getJsonMetadataParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleJsonMetadata_in_ruleModel131);
            this_JsonMetadata_0=ruleJsonMetadata();

            state._fsp--;

             
                    current = this_JsonMetadata_0; 
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleJsonMetadata"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:97:1: entryRuleJsonMetadata returns [EObject current=null] : iv_ruleJsonMetadata= ruleJsonMetadata EOF ;
    public final EObject entryRuleJsonMetadata() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonMetadata = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:98:2: (iv_ruleJsonMetadata= ruleJsonMetadata EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:99:2: iv_ruleJsonMetadata= ruleJsonMetadata EOF
            {
             newCompositeNode(grammarAccess.getJsonMetadataRule()); 
            pushFollow(FOLLOW_ruleJsonMetadata_in_entryRuleJsonMetadata165);
            iv_ruleJsonMetadata=ruleJsonMetadata();

            state._fsp--;

             current =iv_ruleJsonMetadata; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonMetadata175); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonMetadata"


    // $ANTLR start "ruleJsonMetadata"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:106:1: ruleJsonMetadata returns [EObject current=null] : (otherlv_0= '{' ( (lv_pairs_1_0= ruleMetadataPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleMetadataPair ) ) )* otherlv_4= '}' ) ;
    public final EObject ruleJsonMetadata() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_pairs_1_0 = null;

        EObject lv_pairs_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:109:28: ( (otherlv_0= '{' ( (lv_pairs_1_0= ruleMetadataPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleMetadataPair ) ) )* otherlv_4= '}' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:110:1: (otherlv_0= '{' ( (lv_pairs_1_0= ruleMetadataPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleMetadataPair ) ) )* otherlv_4= '}' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:110:1: (otherlv_0= '{' ( (lv_pairs_1_0= ruleMetadataPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleMetadataPair ) ) )* otherlv_4= '}' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:110:3: otherlv_0= '{' ( (lv_pairs_1_0= ruleMetadataPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleMetadataPair ) ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,10,FOLLOW_10_in_ruleJsonMetadata212); 

                	newLeafNode(otherlv_0, grammarAccess.getJsonMetadataAccess().getLeftCurlyBracketKeyword_0());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:114:1: ( (lv_pairs_1_0= ruleMetadataPair ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:115:1: (lv_pairs_1_0= ruleMetadataPair )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:115:1: (lv_pairs_1_0= ruleMetadataPair )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:116:3: lv_pairs_1_0= ruleMetadataPair
            {
             
            	        newCompositeNode(grammarAccess.getJsonMetadataAccess().getPairsMetadataPairParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleMetadataPair_in_ruleJsonMetadata233);
            lv_pairs_1_0=ruleMetadataPair();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getJsonMetadataRule());
            	        }
                   		add(
                   			current, 
                   			"pairs",
                    		lv_pairs_1_0, 
                    		"MetadataPair");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:132:2: (otherlv_2= ',' ( (lv_pairs_3_0= ruleMetadataPair ) ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==11) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:132:4: otherlv_2= ',' ( (lv_pairs_3_0= ruleMetadataPair ) )
            	    {
            	    otherlv_2=(Token)match(input,11,FOLLOW_11_in_ruleJsonMetadata246); 

            	        	newLeafNode(otherlv_2, grammarAccess.getJsonMetadataAccess().getCommaKeyword_2_0());
            	        
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:136:1: ( (lv_pairs_3_0= ruleMetadataPair ) )
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:137:1: (lv_pairs_3_0= ruleMetadataPair )
            	    {
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:137:1: (lv_pairs_3_0= ruleMetadataPair )
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:138:3: lv_pairs_3_0= ruleMetadataPair
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getJsonMetadataAccess().getPairsMetadataPairParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleMetadataPair_in_ruleJsonMetadata267);
            	    lv_pairs_3_0=ruleMetadataPair();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getJsonMetadataRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"pairs",
            	            		lv_pairs_3_0, 
            	            		"MetadataPair");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleJsonMetadata281); 

                	newLeafNode(otherlv_4, grammarAccess.getJsonMetadataAccess().getRightCurlyBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonMetadata"


    // $ANTLR start "entryRuleJsonObject"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:166:1: entryRuleJsonObject returns [EObject current=null] : iv_ruleJsonObject= ruleJsonObject EOF ;
    public final EObject entryRuleJsonObject() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonObject = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:167:2: (iv_ruleJsonObject= ruleJsonObject EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:168:2: iv_ruleJsonObject= ruleJsonObject EOF
            {
             newCompositeNode(grammarAccess.getJsonObjectRule()); 
            pushFollow(FOLLOW_ruleJsonObject_in_entryRuleJsonObject317);
            iv_ruleJsonObject=ruleJsonObject();

            state._fsp--;

             current =iv_ruleJsonObject; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonObject327); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonObject"


    // $ANTLR start "ruleJsonObject"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:175:1: ruleJsonObject returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_pairs_2_0= rulePair ) ) (otherlv_3= ',' ( (lv_pairs_4_0= rulePair ) ) )* )? otherlv_5= '}' ) ;
    public final EObject ruleJsonObject() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_pairs_2_0 = null;

        EObject lv_pairs_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:178:28: ( ( () otherlv_1= '{' ( ( (lv_pairs_2_0= rulePair ) ) (otherlv_3= ',' ( (lv_pairs_4_0= rulePair ) ) )* )? otherlv_5= '}' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:179:1: ( () otherlv_1= '{' ( ( (lv_pairs_2_0= rulePair ) ) (otherlv_3= ',' ( (lv_pairs_4_0= rulePair ) ) )* )? otherlv_5= '}' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:179:1: ( () otherlv_1= '{' ( ( (lv_pairs_2_0= rulePair ) ) (otherlv_3= ',' ( (lv_pairs_4_0= rulePair ) ) )* )? otherlv_5= '}' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:179:2: () otherlv_1= '{' ( ( (lv_pairs_2_0= rulePair ) ) (otherlv_3= ',' ( (lv_pairs_4_0= rulePair ) ) )* )? otherlv_5= '}'
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:179:2: ()
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:180:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getJsonObjectAccess().getJsonObjectAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,10,FOLLOW_10_in_ruleJsonObject373); 

                	newLeafNode(otherlv_1, grammarAccess.getJsonObjectAccess().getLeftCurlyBracketKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:189:1: ( ( (lv_pairs_2_0= rulePair ) ) (otherlv_3= ',' ( (lv_pairs_4_0= rulePair ) ) )* )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==RULE_STRING||LA3_0==13||(LA3_0>=15 && LA3_0<=25)||(LA3_0>=28 && LA3_0<=31)) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:189:2: ( (lv_pairs_2_0= rulePair ) ) (otherlv_3= ',' ( (lv_pairs_4_0= rulePair ) ) )*
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:189:2: ( (lv_pairs_2_0= rulePair ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:190:1: (lv_pairs_2_0= rulePair )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:190:1: (lv_pairs_2_0= rulePair )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:191:3: lv_pairs_2_0= rulePair
                    {
                     
                    	        newCompositeNode(grammarAccess.getJsonObjectAccess().getPairsPairParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_rulePair_in_ruleJsonObject395);
                    lv_pairs_2_0=rulePair();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getJsonObjectRule());
                    	        }
                           		add(
                           			current, 
                           			"pairs",
                            		lv_pairs_2_0, 
                            		"Pair");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:207:2: (otherlv_3= ',' ( (lv_pairs_4_0= rulePair ) ) )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==11) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:207:4: otherlv_3= ',' ( (lv_pairs_4_0= rulePair ) )
                    	    {
                    	    otherlv_3=(Token)match(input,11,FOLLOW_11_in_ruleJsonObject408); 

                    	        	newLeafNode(otherlv_3, grammarAccess.getJsonObjectAccess().getCommaKeyword_2_1_0());
                    	        
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:211:1: ( (lv_pairs_4_0= rulePair ) )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:212:1: (lv_pairs_4_0= rulePair )
                    	    {
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:212:1: (lv_pairs_4_0= rulePair )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:213:3: lv_pairs_4_0= rulePair
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getJsonObjectAccess().getPairsPairParserRuleCall_2_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_rulePair_in_ruleJsonObject429);
                    	    lv_pairs_4_0=rulePair();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getJsonObjectRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"pairs",
                    	            		lv_pairs_4_0, 
                    	            		"Pair");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,12,FOLLOW_12_in_ruleJsonObject445); 

                	newLeafNode(otherlv_5, grammarAccess.getJsonObjectAccess().getRightCurlyBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonObject"


    // $ANTLR start "entryRuleMetadataPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:241:1: entryRuleMetadataPair returns [EObject current=null] : iv_ruleMetadataPair= ruleMetadataPair EOF ;
    public final EObject entryRuleMetadataPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetadataPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:242:2: (iv_ruleMetadataPair= ruleMetadataPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:243:2: iv_ruleMetadataPair= ruleMetadataPair EOF
            {
             newCompositeNode(grammarAccess.getMetadataPairRule()); 
            pushFollow(FOLLOW_ruleMetadataPair_in_entryRuleMetadataPair481);
            iv_ruleMetadataPair=ruleMetadataPair();

            state._fsp--;

             current =iv_ruleMetadataPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMetadataPair491); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMetadataPair"


    // $ANTLR start "ruleMetadataPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:250:1: ruleMetadataPair returns [EObject current=null] : (this_AuthorPair_0= ruleAuthorPair | this_DependenciesPair_1= ruleDependenciesPair | this_IssuesUrlPair_2= ruleIssuesUrlPair | this_LicensePair_3= ruleLicensePair | this_NamePair_4= ruleNamePair | this_ProjectPagePair_5= ruleProjectPagePair | this_SourcePair_6= ruleSourcePair | this_SummaryPair_7= ruleSummaryPair | this_RequirementsPair_8= ruleRequirementsPair | this_OperatingsystemSupportPair_9= ruleOperatingsystemSupportPair | this_TagsPair_10= ruleTagsPair | this_VersionPair_11= ruleVersionPair | this_UnrecognizedMetadataPair_12= ruleUnrecognizedMetadataPair ) ;
    public final EObject ruleMetadataPair() throws RecognitionException {
        EObject current = null;

        EObject this_AuthorPair_0 = null;

        EObject this_DependenciesPair_1 = null;

        EObject this_IssuesUrlPair_2 = null;

        EObject this_LicensePair_3 = null;

        EObject this_NamePair_4 = null;

        EObject this_ProjectPagePair_5 = null;

        EObject this_SourcePair_6 = null;

        EObject this_SummaryPair_7 = null;

        EObject this_RequirementsPair_8 = null;

        EObject this_OperatingsystemSupportPair_9 = null;

        EObject this_TagsPair_10 = null;

        EObject this_VersionPair_11 = null;

        EObject this_UnrecognizedMetadataPair_12 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:253:28: ( (this_AuthorPair_0= ruleAuthorPair | this_DependenciesPair_1= ruleDependenciesPair | this_IssuesUrlPair_2= ruleIssuesUrlPair | this_LicensePair_3= ruleLicensePair | this_NamePair_4= ruleNamePair | this_ProjectPagePair_5= ruleProjectPagePair | this_SourcePair_6= ruleSourcePair | this_SummaryPair_7= ruleSummaryPair | this_RequirementsPair_8= ruleRequirementsPair | this_OperatingsystemSupportPair_9= ruleOperatingsystemSupportPair | this_TagsPair_10= ruleTagsPair | this_VersionPair_11= ruleVersionPair | this_UnrecognizedMetadataPair_12= ruleUnrecognizedMetadataPair ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:254:1: (this_AuthorPair_0= ruleAuthorPair | this_DependenciesPair_1= ruleDependenciesPair | this_IssuesUrlPair_2= ruleIssuesUrlPair | this_LicensePair_3= ruleLicensePair | this_NamePair_4= ruleNamePair | this_ProjectPagePair_5= ruleProjectPagePair | this_SourcePair_6= ruleSourcePair | this_SummaryPair_7= ruleSummaryPair | this_RequirementsPair_8= ruleRequirementsPair | this_OperatingsystemSupportPair_9= ruleOperatingsystemSupportPair | this_TagsPair_10= ruleTagsPair | this_VersionPair_11= ruleVersionPair | this_UnrecognizedMetadataPair_12= ruleUnrecognizedMetadataPair )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:254:1: (this_AuthorPair_0= ruleAuthorPair | this_DependenciesPair_1= ruleDependenciesPair | this_IssuesUrlPair_2= ruleIssuesUrlPair | this_LicensePair_3= ruleLicensePair | this_NamePair_4= ruleNamePair | this_ProjectPagePair_5= ruleProjectPagePair | this_SourcePair_6= ruleSourcePair | this_SummaryPair_7= ruleSummaryPair | this_RequirementsPair_8= ruleRequirementsPair | this_OperatingsystemSupportPair_9= ruleOperatingsystemSupportPair | this_TagsPair_10= ruleTagsPair | this_VersionPair_11= ruleVersionPair | this_UnrecognizedMetadataPair_12= ruleUnrecognizedMetadataPair )
            int alt4=13;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt4=1;
                }
                break;
            case 15:
                {
                alt4=2;
                }
                break;
            case 16:
                {
                alt4=3;
                }
                break;
            case 17:
                {
                alt4=4;
                }
                break;
            case 18:
                {
                alt4=5;
                }
                break;
            case 20:
                {
                alt4=6;
                }
                break;
            case 22:
                {
                alt4=7;
                }
                break;
            case 23:
                {
                alt4=8;
                }
                break;
            case 21:
                {
                alt4=9;
                }
                break;
            case 19:
                {
                alt4=10;
                }
                break;
            case 24:
                {
                alt4=11;
                }
                break;
            case 25:
                {
                alt4=12;
                }
                break;
            case RULE_STRING:
                {
                alt4=13;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:255:5: this_AuthorPair_0= ruleAuthorPair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getAuthorPairParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleAuthorPair_in_ruleMetadataPair538);
                    this_AuthorPair_0=ruleAuthorPair();

                    state._fsp--;

                     
                            current = this_AuthorPair_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:265:5: this_DependenciesPair_1= ruleDependenciesPair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getDependenciesPairParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleDependenciesPair_in_ruleMetadataPair565);
                    this_DependenciesPair_1=ruleDependenciesPair();

                    state._fsp--;

                     
                            current = this_DependenciesPair_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:275:5: this_IssuesUrlPair_2= ruleIssuesUrlPair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getIssuesUrlPairParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleIssuesUrlPair_in_ruleMetadataPair592);
                    this_IssuesUrlPair_2=ruleIssuesUrlPair();

                    state._fsp--;

                     
                            current = this_IssuesUrlPair_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:285:5: this_LicensePair_3= ruleLicensePair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getLicensePairParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_ruleLicensePair_in_ruleMetadataPair619);
                    this_LicensePair_3=ruleLicensePair();

                    state._fsp--;

                     
                            current = this_LicensePair_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:295:5: this_NamePair_4= ruleNamePair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getNamePairParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_ruleNamePair_in_ruleMetadataPair646);
                    this_NamePair_4=ruleNamePair();

                    state._fsp--;

                     
                            current = this_NamePair_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:305:5: this_ProjectPagePair_5= ruleProjectPagePair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getProjectPagePairParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleProjectPagePair_in_ruleMetadataPair673);
                    this_ProjectPagePair_5=ruleProjectPagePair();

                    state._fsp--;

                     
                            current = this_ProjectPagePair_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:315:5: this_SourcePair_6= ruleSourcePair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getSourcePairParserRuleCall_6()); 
                        
                    pushFollow(FOLLOW_ruleSourcePair_in_ruleMetadataPair700);
                    this_SourcePair_6=ruleSourcePair();

                    state._fsp--;

                     
                            current = this_SourcePair_6; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 8 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:325:5: this_SummaryPair_7= ruleSummaryPair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getSummaryPairParserRuleCall_7()); 
                        
                    pushFollow(FOLLOW_ruleSummaryPair_in_ruleMetadataPair727);
                    this_SummaryPair_7=ruleSummaryPair();

                    state._fsp--;

                     
                            current = this_SummaryPair_7; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 9 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:335:5: this_RequirementsPair_8= ruleRequirementsPair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getRequirementsPairParserRuleCall_8()); 
                        
                    pushFollow(FOLLOW_ruleRequirementsPair_in_ruleMetadataPair754);
                    this_RequirementsPair_8=ruleRequirementsPair();

                    state._fsp--;

                     
                            current = this_RequirementsPair_8; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 10 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:345:5: this_OperatingsystemSupportPair_9= ruleOperatingsystemSupportPair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getOperatingsystemSupportPairParserRuleCall_9()); 
                        
                    pushFollow(FOLLOW_ruleOperatingsystemSupportPair_in_ruleMetadataPair781);
                    this_OperatingsystemSupportPair_9=ruleOperatingsystemSupportPair();

                    state._fsp--;

                     
                            current = this_OperatingsystemSupportPair_9; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 11 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:355:5: this_TagsPair_10= ruleTagsPair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getTagsPairParserRuleCall_10()); 
                        
                    pushFollow(FOLLOW_ruleTagsPair_in_ruleMetadataPair808);
                    this_TagsPair_10=ruleTagsPair();

                    state._fsp--;

                     
                            current = this_TagsPair_10; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 12 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:365:5: this_VersionPair_11= ruleVersionPair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getVersionPairParserRuleCall_11()); 
                        
                    pushFollow(FOLLOW_ruleVersionPair_in_ruleMetadataPair835);
                    this_VersionPair_11=ruleVersionPair();

                    state._fsp--;

                     
                            current = this_VersionPair_11; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 13 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:375:5: this_UnrecognizedMetadataPair_12= ruleUnrecognizedMetadataPair
                    {
                     
                            newCompositeNode(grammarAccess.getMetadataPairAccess().getUnrecognizedMetadataPairParserRuleCall_12()); 
                        
                    pushFollow(FOLLOW_ruleUnrecognizedMetadataPair_in_ruleMetadataPair862);
                    this_UnrecognizedMetadataPair_12=ruleUnrecognizedMetadataPair();

                    state._fsp--;

                     
                            current = this_UnrecognizedMetadataPair_12; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMetadataPair"


    // $ANTLR start "entryRuleAuthorPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:391:1: entryRuleAuthorPair returns [EObject current=null] : iv_ruleAuthorPair= ruleAuthorPair EOF ;
    public final EObject entryRuleAuthorPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAuthorPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:392:2: (iv_ruleAuthorPair= ruleAuthorPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:393:2: iv_ruleAuthorPair= ruleAuthorPair EOF
            {
             newCompositeNode(grammarAccess.getAuthorPairRule()); 
            pushFollow(FOLLOW_ruleAuthorPair_in_entryRuleAuthorPair897);
            iv_ruleAuthorPair=ruleAuthorPair();

            state._fsp--;

             current =iv_ruleAuthorPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAuthorPair907); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAuthorPair"


    // $ANTLR start "ruleAuthorPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:400:1: ruleAuthorPair returns [EObject current=null] : ( ( (lv_name_0_0= '\"author\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) ;
    public final EObject ruleAuthorPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:403:28: ( ( ( (lv_name_0_0= '\"author\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:404:1: ( ( (lv_name_0_0= '\"author\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:404:1: ( ( (lv_name_0_0= '\"author\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:404:2: ( (lv_name_0_0= '\"author\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:404:2: ( (lv_name_0_0= '\"author\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:405:1: (lv_name_0_0= '\"author\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:405:1: (lv_name_0_0= '\"author\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:406:3: lv_name_0_0= '\"author\"'
            {
            lv_name_0_0=(Token)match(input,13,FOLLOW_13_in_ruleAuthorPair950); 

                    newLeafNode(lv_name_0_0, grammarAccess.getAuthorPairAccess().getNameAuthorKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getAuthorPairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"author\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleAuthorPair975); 

                	newLeafNode(otherlv_1, grammarAccess.getAuthorPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:423:1: ( (lv_value_2_0= ruleStringLiteral ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:424:1: (lv_value_2_0= ruleStringLiteral )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:424:1: (lv_value_2_0= ruleStringLiteral )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:425:3: lv_value_2_0= ruleStringLiteral
            {
             
            	        newCompositeNode(grammarAccess.getAuthorPairAccess().getValueStringLiteralParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleStringLiteral_in_ruleAuthorPair996);
            lv_value_2_0=ruleStringLiteral();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAuthorPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"StringLiteral");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAuthorPair"


    // $ANTLR start "entryRuleDependenciesPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:449:1: entryRuleDependenciesPair returns [EObject current=null] : iv_ruleDependenciesPair= ruleDependenciesPair EOF ;
    public final EObject entryRuleDependenciesPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDependenciesPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:450:2: (iv_ruleDependenciesPair= ruleDependenciesPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:451:2: iv_ruleDependenciesPair= ruleDependenciesPair EOF
            {
             newCompositeNode(grammarAccess.getDependenciesPairRule()); 
            pushFollow(FOLLOW_ruleDependenciesPair_in_entryRuleDependenciesPair1032);
            iv_ruleDependenciesPair=ruleDependenciesPair();

            state._fsp--;

             current =iv_ruleDependenciesPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDependenciesPair1042); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDependenciesPair"


    // $ANTLR start "ruleDependenciesPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:458:1: ruleDependenciesPair returns [EObject current=null] : ( ( (lv_name_0_0= '\"dependencies\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleDependencyArray ) ) ) ;
    public final EObject ruleDependenciesPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:461:28: ( ( ( (lv_name_0_0= '\"dependencies\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleDependencyArray ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:462:1: ( ( (lv_name_0_0= '\"dependencies\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleDependencyArray ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:462:1: ( ( (lv_name_0_0= '\"dependencies\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleDependencyArray ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:462:2: ( (lv_name_0_0= '\"dependencies\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleDependencyArray ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:462:2: ( (lv_name_0_0= '\"dependencies\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:463:1: (lv_name_0_0= '\"dependencies\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:463:1: (lv_name_0_0= '\"dependencies\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:464:3: lv_name_0_0= '\"dependencies\"'
            {
            lv_name_0_0=(Token)match(input,15,FOLLOW_15_in_ruleDependenciesPair1085); 

                    newLeafNode(lv_name_0_0, grammarAccess.getDependenciesPairAccess().getNameDependenciesKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getDependenciesPairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"dependencies\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleDependenciesPair1110); 

                	newLeafNode(otherlv_1, grammarAccess.getDependenciesPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:481:1: ( (lv_value_2_0= ruleDependencyArray ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:482:1: (lv_value_2_0= ruleDependencyArray )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:482:1: (lv_value_2_0= ruleDependencyArray )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:483:3: lv_value_2_0= ruleDependencyArray
            {
             
            	        newCompositeNode(grammarAccess.getDependenciesPairAccess().getValueDependencyArrayParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleDependencyArray_in_ruleDependenciesPair1131);
            lv_value_2_0=ruleDependencyArray();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getDependenciesPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"DependencyArray");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDependenciesPair"


    // $ANTLR start "entryRuleIssuesUrlPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:507:1: entryRuleIssuesUrlPair returns [EObject current=null] : iv_ruleIssuesUrlPair= ruleIssuesUrlPair EOF ;
    public final EObject entryRuleIssuesUrlPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIssuesUrlPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:508:2: (iv_ruleIssuesUrlPair= ruleIssuesUrlPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:509:2: iv_ruleIssuesUrlPair= ruleIssuesUrlPair EOF
            {
             newCompositeNode(grammarAccess.getIssuesUrlPairRule()); 
            pushFollow(FOLLOW_ruleIssuesUrlPair_in_entryRuleIssuesUrlPair1167);
            iv_ruleIssuesUrlPair=ruleIssuesUrlPair();

            state._fsp--;

             current =iv_ruleIssuesUrlPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleIssuesUrlPair1177); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIssuesUrlPair"


    // $ANTLR start "ruleIssuesUrlPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:516:1: ruleIssuesUrlPair returns [EObject current=null] : ( ( (lv_name_0_0= '\"issues_url\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) ;
    public final EObject ruleIssuesUrlPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:519:28: ( ( ( (lv_name_0_0= '\"issues_url\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:520:1: ( ( (lv_name_0_0= '\"issues_url\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:520:1: ( ( (lv_name_0_0= '\"issues_url\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:520:2: ( (lv_name_0_0= '\"issues_url\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:520:2: ( (lv_name_0_0= '\"issues_url\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:521:1: (lv_name_0_0= '\"issues_url\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:521:1: (lv_name_0_0= '\"issues_url\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:522:3: lv_name_0_0= '\"issues_url\"'
            {
            lv_name_0_0=(Token)match(input,16,FOLLOW_16_in_ruleIssuesUrlPair1220); 

                    newLeafNode(lv_name_0_0, grammarAccess.getIssuesUrlPairAccess().getNameIssues_urlKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getIssuesUrlPairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"issues_url\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleIssuesUrlPair1245); 

                	newLeafNode(otherlv_1, grammarAccess.getIssuesUrlPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:539:1: ( (lv_value_2_0= ruleStringLiteral ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:540:1: (lv_value_2_0= ruleStringLiteral )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:540:1: (lv_value_2_0= ruleStringLiteral )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:541:3: lv_value_2_0= ruleStringLiteral
            {
             
            	        newCompositeNode(grammarAccess.getIssuesUrlPairAccess().getValueStringLiteralParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleStringLiteral_in_ruleIssuesUrlPair1266);
            lv_value_2_0=ruleStringLiteral();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getIssuesUrlPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"StringLiteral");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIssuesUrlPair"


    // $ANTLR start "entryRuleLicensePair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:565:1: entryRuleLicensePair returns [EObject current=null] : iv_ruleLicensePair= ruleLicensePair EOF ;
    public final EObject entryRuleLicensePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLicensePair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:566:2: (iv_ruleLicensePair= ruleLicensePair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:567:2: iv_ruleLicensePair= ruleLicensePair EOF
            {
             newCompositeNode(grammarAccess.getLicensePairRule()); 
            pushFollow(FOLLOW_ruleLicensePair_in_entryRuleLicensePair1302);
            iv_ruleLicensePair=ruleLicensePair();

            state._fsp--;

             current =iv_ruleLicensePair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLicensePair1312); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLicensePair"


    // $ANTLR start "ruleLicensePair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:574:1: ruleLicensePair returns [EObject current=null] : ( ( (lv_name_0_0= '\"license\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) ;
    public final EObject ruleLicensePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:577:28: ( ( ( (lv_name_0_0= '\"license\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:578:1: ( ( (lv_name_0_0= '\"license\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:578:1: ( ( (lv_name_0_0= '\"license\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:578:2: ( (lv_name_0_0= '\"license\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:578:2: ( (lv_name_0_0= '\"license\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:579:1: (lv_name_0_0= '\"license\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:579:1: (lv_name_0_0= '\"license\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:580:3: lv_name_0_0= '\"license\"'
            {
            lv_name_0_0=(Token)match(input,17,FOLLOW_17_in_ruleLicensePair1355); 

                    newLeafNode(lv_name_0_0, grammarAccess.getLicensePairAccess().getNameLicenseKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLicensePairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"license\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleLicensePair1380); 

                	newLeafNode(otherlv_1, grammarAccess.getLicensePairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:597:1: ( (lv_value_2_0= ruleStringLiteral ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:598:1: (lv_value_2_0= ruleStringLiteral )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:598:1: (lv_value_2_0= ruleStringLiteral )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:599:3: lv_value_2_0= ruleStringLiteral
            {
             
            	        newCompositeNode(grammarAccess.getLicensePairAccess().getValueStringLiteralParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleStringLiteral_in_ruleLicensePair1401);
            lv_value_2_0=ruleStringLiteral();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLicensePairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"StringLiteral");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLicensePair"


    // $ANTLR start "entryRuleNamePair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:623:1: entryRuleNamePair returns [EObject current=null] : iv_ruleNamePair= ruleNamePair EOF ;
    public final EObject entryRuleNamePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNamePair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:624:2: (iv_ruleNamePair= ruleNamePair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:625:2: iv_ruleNamePair= ruleNamePair EOF
            {
             newCompositeNode(grammarAccess.getNamePairRule()); 
            pushFollow(FOLLOW_ruleNamePair_in_entryRuleNamePair1437);
            iv_ruleNamePair=ruleNamePair();

            state._fsp--;

             current =iv_ruleNamePair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNamePair1447); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNamePair"


    // $ANTLR start "ruleNamePair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:632:1: ruleNamePair returns [EObject current=null] : ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleModuleName ) ) ) ;
    public final EObject ruleNamePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:635:28: ( ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleModuleName ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:636:1: ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleModuleName ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:636:1: ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleModuleName ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:636:2: ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleModuleName ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:636:2: ( (lv_name_0_0= '\"name\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:637:1: (lv_name_0_0= '\"name\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:637:1: (lv_name_0_0= '\"name\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:638:3: lv_name_0_0= '\"name\"'
            {
            lv_name_0_0=(Token)match(input,18,FOLLOW_18_in_ruleNamePair1490); 

                    newLeafNode(lv_name_0_0, grammarAccess.getNamePairAccess().getNameNameKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getNamePairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"name\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleNamePair1515); 

                	newLeafNode(otherlv_1, grammarAccess.getNamePairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:655:1: ( (lv_value_2_0= ruleModuleName ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:656:1: (lv_value_2_0= ruleModuleName )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:656:1: (lv_value_2_0= ruleModuleName )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:657:3: lv_value_2_0= ruleModuleName
            {
             
            	        newCompositeNode(grammarAccess.getNamePairAccess().getValueModuleNameParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleModuleName_in_ruleNamePair1536);
            lv_value_2_0=ruleModuleName();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getNamePairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"ModuleName");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNamePair"


    // $ANTLR start "entryRuleOperatingsystemSupportPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:681:1: entryRuleOperatingsystemSupportPair returns [EObject current=null] : iv_ruleOperatingsystemSupportPair= ruleOperatingsystemSupportPair EOF ;
    public final EObject entryRuleOperatingsystemSupportPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOperatingsystemSupportPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:682:2: (iv_ruleOperatingsystemSupportPair= ruleOperatingsystemSupportPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:683:2: iv_ruleOperatingsystemSupportPair= ruleOperatingsystemSupportPair EOF
            {
             newCompositeNode(grammarAccess.getOperatingsystemSupportPairRule()); 
            pushFollow(FOLLOW_ruleOperatingsystemSupportPair_in_entryRuleOperatingsystemSupportPair1572);
            iv_ruleOperatingsystemSupportPair=ruleOperatingsystemSupportPair();

            state._fsp--;

             current =iv_ruleOperatingsystemSupportPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOperatingsystemSupportPair1582); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOperatingsystemSupportPair"


    // $ANTLR start "ruleOperatingsystemSupportPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:690:1: ruleOperatingsystemSupportPair returns [EObject current=null] : ( ( (lv_name_0_0= '\"operatingsystem_support\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleOSArray ) ) ) ;
    public final EObject ruleOperatingsystemSupportPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:693:28: ( ( ( (lv_name_0_0= '\"operatingsystem_support\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleOSArray ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:694:1: ( ( (lv_name_0_0= '\"operatingsystem_support\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleOSArray ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:694:1: ( ( (lv_name_0_0= '\"operatingsystem_support\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleOSArray ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:694:2: ( (lv_name_0_0= '\"operatingsystem_support\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleOSArray ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:694:2: ( (lv_name_0_0= '\"operatingsystem_support\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:695:1: (lv_name_0_0= '\"operatingsystem_support\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:695:1: (lv_name_0_0= '\"operatingsystem_support\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:696:3: lv_name_0_0= '\"operatingsystem_support\"'
            {
            lv_name_0_0=(Token)match(input,19,FOLLOW_19_in_ruleOperatingsystemSupportPair1625); 

                    newLeafNode(lv_name_0_0, grammarAccess.getOperatingsystemSupportPairAccess().getNameOperatingsystem_supportKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getOperatingsystemSupportPairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"operatingsystem_support\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleOperatingsystemSupportPair1650); 

                	newLeafNode(otherlv_1, grammarAccess.getOperatingsystemSupportPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:713:1: ( (lv_value_2_0= ruleOSArray ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:714:1: (lv_value_2_0= ruleOSArray )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:714:1: (lv_value_2_0= ruleOSArray )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:715:3: lv_value_2_0= ruleOSArray
            {
             
            	        newCompositeNode(grammarAccess.getOperatingsystemSupportPairAccess().getValueOSArrayParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleOSArray_in_ruleOperatingsystemSupportPair1671);
            lv_value_2_0=ruleOSArray();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getOperatingsystemSupportPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"OSArray");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOperatingsystemSupportPair"


    // $ANTLR start "entryRuleProjectPagePair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:739:1: entryRuleProjectPagePair returns [EObject current=null] : iv_ruleProjectPagePair= ruleProjectPagePair EOF ;
    public final EObject entryRuleProjectPagePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProjectPagePair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:740:2: (iv_ruleProjectPagePair= ruleProjectPagePair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:741:2: iv_ruleProjectPagePair= ruleProjectPagePair EOF
            {
             newCompositeNode(grammarAccess.getProjectPagePairRule()); 
            pushFollow(FOLLOW_ruleProjectPagePair_in_entryRuleProjectPagePair1707);
            iv_ruleProjectPagePair=ruleProjectPagePair();

            state._fsp--;

             current =iv_ruleProjectPagePair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleProjectPagePair1717); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProjectPagePair"


    // $ANTLR start "ruleProjectPagePair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:748:1: ruleProjectPagePair returns [EObject current=null] : ( ( (lv_name_0_0= '\"project_page\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) ;
    public final EObject ruleProjectPagePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:751:28: ( ( ( (lv_name_0_0= '\"project_page\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:752:1: ( ( (lv_name_0_0= '\"project_page\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:752:1: ( ( (lv_name_0_0= '\"project_page\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:752:2: ( (lv_name_0_0= '\"project_page\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:752:2: ( (lv_name_0_0= '\"project_page\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:753:1: (lv_name_0_0= '\"project_page\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:753:1: (lv_name_0_0= '\"project_page\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:754:3: lv_name_0_0= '\"project_page\"'
            {
            lv_name_0_0=(Token)match(input,20,FOLLOW_20_in_ruleProjectPagePair1760); 

                    newLeafNode(lv_name_0_0, grammarAccess.getProjectPagePairAccess().getNameProject_pageKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getProjectPagePairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"project_page\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleProjectPagePair1785); 

                	newLeafNode(otherlv_1, grammarAccess.getProjectPagePairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:771:1: ( (lv_value_2_0= ruleStringLiteral ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:772:1: (lv_value_2_0= ruleStringLiteral )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:772:1: (lv_value_2_0= ruleStringLiteral )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:773:3: lv_value_2_0= ruleStringLiteral
            {
             
            	        newCompositeNode(grammarAccess.getProjectPagePairAccess().getValueStringLiteralParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleStringLiteral_in_ruleProjectPagePair1806);
            lv_value_2_0=ruleStringLiteral();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getProjectPagePairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"StringLiteral");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProjectPagePair"


    // $ANTLR start "entryRuleRequirementsPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:797:1: entryRuleRequirementsPair returns [EObject current=null] : iv_ruleRequirementsPair= ruleRequirementsPair EOF ;
    public final EObject entryRuleRequirementsPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequirementsPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:798:2: (iv_ruleRequirementsPair= ruleRequirementsPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:799:2: iv_ruleRequirementsPair= ruleRequirementsPair EOF
            {
             newCompositeNode(grammarAccess.getRequirementsPairRule()); 
            pushFollow(FOLLOW_ruleRequirementsPair_in_entryRuleRequirementsPair1842);
            iv_ruleRequirementsPair=ruleRequirementsPair();

            state._fsp--;

             current =iv_ruleRequirementsPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRequirementsPair1852); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRequirementsPair"


    // $ANTLR start "ruleRequirementsPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:806:1: ruleRequirementsPair returns [EObject current=null] : ( ( (lv_name_0_0= '\"requirements\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementArray ) ) ) ;
    public final EObject ruleRequirementsPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:809:28: ( ( ( (lv_name_0_0= '\"requirements\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementArray ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:810:1: ( ( (lv_name_0_0= '\"requirements\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementArray ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:810:1: ( ( (lv_name_0_0= '\"requirements\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementArray ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:810:2: ( (lv_name_0_0= '\"requirements\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementArray ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:810:2: ( (lv_name_0_0= '\"requirements\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:811:1: (lv_name_0_0= '\"requirements\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:811:1: (lv_name_0_0= '\"requirements\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:812:3: lv_name_0_0= '\"requirements\"'
            {
            lv_name_0_0=(Token)match(input,21,FOLLOW_21_in_ruleRequirementsPair1895); 

                    newLeafNode(lv_name_0_0, grammarAccess.getRequirementsPairAccess().getNameRequirementsKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getRequirementsPairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"requirements\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleRequirementsPair1920); 

                	newLeafNode(otherlv_1, grammarAccess.getRequirementsPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:829:1: ( (lv_value_2_0= ruleRequirementArray ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:830:1: (lv_value_2_0= ruleRequirementArray )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:830:1: (lv_value_2_0= ruleRequirementArray )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:831:3: lv_value_2_0= ruleRequirementArray
            {
             
            	        newCompositeNode(grammarAccess.getRequirementsPairAccess().getValueRequirementArrayParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleRequirementArray_in_ruleRequirementsPair1941);
            lv_value_2_0=ruleRequirementArray();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getRequirementsPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"RequirementArray");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRequirementsPair"


    // $ANTLR start "entryRuleSourcePair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:855:1: entryRuleSourcePair returns [EObject current=null] : iv_ruleSourcePair= ruleSourcePair EOF ;
    public final EObject entryRuleSourcePair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSourcePair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:856:2: (iv_ruleSourcePair= ruleSourcePair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:857:2: iv_ruleSourcePair= ruleSourcePair EOF
            {
             newCompositeNode(grammarAccess.getSourcePairRule()); 
            pushFollow(FOLLOW_ruleSourcePair_in_entryRuleSourcePair1977);
            iv_ruleSourcePair=ruleSourcePair();

            state._fsp--;

             current =iv_ruleSourcePair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleSourcePair1987); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSourcePair"


    // $ANTLR start "ruleSourcePair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:864:1: ruleSourcePair returns [EObject current=null] : ( ( (lv_name_0_0= '\"source\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) ;
    public final EObject ruleSourcePair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:867:28: ( ( ( (lv_name_0_0= '\"source\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:868:1: ( ( (lv_name_0_0= '\"source\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:868:1: ( ( (lv_name_0_0= '\"source\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:868:2: ( (lv_name_0_0= '\"source\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:868:2: ( (lv_name_0_0= '\"source\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:869:1: (lv_name_0_0= '\"source\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:869:1: (lv_name_0_0= '\"source\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:870:3: lv_name_0_0= '\"source\"'
            {
            lv_name_0_0=(Token)match(input,22,FOLLOW_22_in_ruleSourcePair2030); 

                    newLeafNode(lv_name_0_0, grammarAccess.getSourcePairAccess().getNameSourceKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getSourcePairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"source\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleSourcePair2055); 

                	newLeafNode(otherlv_1, grammarAccess.getSourcePairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:887:1: ( (lv_value_2_0= ruleStringLiteral ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:888:1: (lv_value_2_0= ruleStringLiteral )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:888:1: (lv_value_2_0= ruleStringLiteral )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:889:3: lv_value_2_0= ruleStringLiteral
            {
             
            	        newCompositeNode(grammarAccess.getSourcePairAccess().getValueStringLiteralParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleStringLiteral_in_ruleSourcePair2076);
            lv_value_2_0=ruleStringLiteral();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getSourcePairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"StringLiteral");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSourcePair"


    // $ANTLR start "entryRuleSummaryPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:913:1: entryRuleSummaryPair returns [EObject current=null] : iv_ruleSummaryPair= ruleSummaryPair EOF ;
    public final EObject entryRuleSummaryPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSummaryPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:914:2: (iv_ruleSummaryPair= ruleSummaryPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:915:2: iv_ruleSummaryPair= ruleSummaryPair EOF
            {
             newCompositeNode(grammarAccess.getSummaryPairRule()); 
            pushFollow(FOLLOW_ruleSummaryPair_in_entryRuleSummaryPair2112);
            iv_ruleSummaryPair=ruleSummaryPair();

            state._fsp--;

             current =iv_ruleSummaryPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleSummaryPair2122); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSummaryPair"


    // $ANTLR start "ruleSummaryPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:922:1: ruleSummaryPair returns [EObject current=null] : ( ( (lv_name_0_0= '\"summary\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) ;
    public final EObject ruleSummaryPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:925:28: ( ( ( (lv_name_0_0= '\"summary\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:926:1: ( ( (lv_name_0_0= '\"summary\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:926:1: ( ( (lv_name_0_0= '\"summary\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:926:2: ( (lv_name_0_0= '\"summary\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:926:2: ( (lv_name_0_0= '\"summary\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:927:1: (lv_name_0_0= '\"summary\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:927:1: (lv_name_0_0= '\"summary\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:928:3: lv_name_0_0= '\"summary\"'
            {
            lv_name_0_0=(Token)match(input,23,FOLLOW_23_in_ruleSummaryPair2165); 

                    newLeafNode(lv_name_0_0, grammarAccess.getSummaryPairAccess().getNameSummaryKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getSummaryPairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"summary\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleSummaryPair2190); 

                	newLeafNode(otherlv_1, grammarAccess.getSummaryPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:945:1: ( (lv_value_2_0= ruleStringLiteral ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:946:1: (lv_value_2_0= ruleStringLiteral )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:946:1: (lv_value_2_0= ruleStringLiteral )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:947:3: lv_value_2_0= ruleStringLiteral
            {
             
            	        newCompositeNode(grammarAccess.getSummaryPairAccess().getValueStringLiteralParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleStringLiteral_in_ruleSummaryPair2211);
            lv_value_2_0=ruleStringLiteral();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getSummaryPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"StringLiteral");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSummaryPair"


    // $ANTLR start "entryRuleTagsPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:971:1: entryRuleTagsPair returns [EObject current=null] : iv_ruleTagsPair= ruleTagsPair EOF ;
    public final EObject entryRuleTagsPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTagsPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:972:2: (iv_ruleTagsPair= ruleTagsPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:973:2: iv_ruleTagsPair= ruleTagsPair EOF
            {
             newCompositeNode(grammarAccess.getTagsPairRule()); 
            pushFollow(FOLLOW_ruleTagsPair_in_entryRuleTagsPair2247);
            iv_ruleTagsPair=ruleTagsPair();

            state._fsp--;

             current =iv_ruleTagsPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTagsPair2257); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTagsPair"


    // $ANTLR start "ruleTagsPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:980:1: ruleTagsPair returns [EObject current=null] : ( ( (lv_name_0_0= '\"tags\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleTagArray ) ) ) ;
    public final EObject ruleTagsPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:983:28: ( ( ( (lv_name_0_0= '\"tags\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleTagArray ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:984:1: ( ( (lv_name_0_0= '\"tags\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleTagArray ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:984:1: ( ( (lv_name_0_0= '\"tags\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleTagArray ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:984:2: ( (lv_name_0_0= '\"tags\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleTagArray ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:984:2: ( (lv_name_0_0= '\"tags\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:985:1: (lv_name_0_0= '\"tags\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:985:1: (lv_name_0_0= '\"tags\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:986:3: lv_name_0_0= '\"tags\"'
            {
            lv_name_0_0=(Token)match(input,24,FOLLOW_24_in_ruleTagsPair2300); 

                    newLeafNode(lv_name_0_0, grammarAccess.getTagsPairAccess().getNameTagsKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getTagsPairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"tags\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleTagsPair2325); 

                	newLeafNode(otherlv_1, grammarAccess.getTagsPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1003:1: ( (lv_value_2_0= ruleTagArray ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1004:1: (lv_value_2_0= ruleTagArray )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1004:1: (lv_value_2_0= ruleTagArray )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1005:3: lv_value_2_0= ruleTagArray
            {
             
            	        newCompositeNode(grammarAccess.getTagsPairAccess().getValueTagArrayParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleTagArray_in_ruleTagsPair2346);
            lv_value_2_0=ruleTagArray();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getTagsPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"TagArray");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTagsPair"


    // $ANTLR start "entryRuleVersionPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1029:1: entryRuleVersionPair returns [EObject current=null] : iv_ruleVersionPair= ruleVersionPair EOF ;
    public final EObject entryRuleVersionPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1030:2: (iv_ruleVersionPair= ruleVersionPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1031:2: iv_ruleVersionPair= ruleVersionPair EOF
            {
             newCompositeNode(grammarAccess.getVersionPairRule()); 
            pushFollow(FOLLOW_ruleVersionPair_in_entryRuleVersionPair2382);
            iv_ruleVersionPair=ruleVersionPair();

            state._fsp--;

             current =iv_ruleVersionPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVersionPair2392); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVersionPair"


    // $ANTLR start "ruleVersionPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1038:1: ruleVersionPair returns [EObject current=null] : ( ( (lv_name_0_0= '\"version\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleVersion ) ) ) ;
    public final EObject ruleVersionPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1041:28: ( ( ( (lv_name_0_0= '\"version\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleVersion ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1042:1: ( ( (lv_name_0_0= '\"version\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleVersion ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1042:1: ( ( (lv_name_0_0= '\"version\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleVersion ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1042:2: ( (lv_name_0_0= '\"version\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleVersion ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1042:2: ( (lv_name_0_0= '\"version\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1043:1: (lv_name_0_0= '\"version\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1043:1: (lv_name_0_0= '\"version\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1044:3: lv_name_0_0= '\"version\"'
            {
            lv_name_0_0=(Token)match(input,25,FOLLOW_25_in_ruleVersionPair2435); 

                    newLeafNode(lv_name_0_0, grammarAccess.getVersionPairAccess().getNameVersionKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getVersionPairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"version\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleVersionPair2460); 

                	newLeafNode(otherlv_1, grammarAccess.getVersionPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1061:1: ( (lv_value_2_0= ruleVersion ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1062:1: (lv_value_2_0= ruleVersion )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1062:1: (lv_value_2_0= ruleVersion )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1063:3: lv_value_2_0= ruleVersion
            {
             
            	        newCompositeNode(grammarAccess.getVersionPairAccess().getValueVersionParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleVersion_in_ruleVersionPair2481);
            lv_value_2_0=ruleVersion();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getVersionPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"Version");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersionPair"


    // $ANTLR start "entryRuleUnrecognizedMetadataPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1087:1: entryRuleUnrecognizedMetadataPair returns [EObject current=null] : iv_ruleUnrecognizedMetadataPair= ruleUnrecognizedMetadataPair EOF ;
    public final EObject entryRuleUnrecognizedMetadataPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnrecognizedMetadataPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1088:2: (iv_ruleUnrecognizedMetadataPair= ruleUnrecognizedMetadataPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1089:2: iv_ruleUnrecognizedMetadataPair= ruleUnrecognizedMetadataPair EOF
            {
             newCompositeNode(grammarAccess.getUnrecognizedMetadataPairRule()); 
            pushFollow(FOLLOW_ruleUnrecognizedMetadataPair_in_entryRuleUnrecognizedMetadataPair2517);
            iv_ruleUnrecognizedMetadataPair=ruleUnrecognizedMetadataPair();

            state._fsp--;

             current =iv_ruleUnrecognizedMetadataPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnrecognizedMetadataPair2527); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnrecognizedMetadataPair"


    // $ANTLR start "ruleUnrecognizedMetadataPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1096:1: ruleUnrecognizedMetadataPair returns [EObject current=null] : ( ( (lv_name_0_0= RULE_STRING ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleUnrecognizedMetadataPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1099:28: ( ( ( (lv_name_0_0= RULE_STRING ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1100:1: ( ( (lv_name_0_0= RULE_STRING ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1100:1: ( ( (lv_name_0_0= RULE_STRING ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1100:2: ( (lv_name_0_0= RULE_STRING ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1100:2: ( (lv_name_0_0= RULE_STRING ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1101:1: (lv_name_0_0= RULE_STRING )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1101:1: (lv_name_0_0= RULE_STRING )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1102:3: lv_name_0_0= RULE_STRING
            {
            lv_name_0_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleUnrecognizedMetadataPair2569); 

            			newLeafNode(lv_name_0_0, grammarAccess.getUnrecognizedMetadataPairAccess().getNameSTRINGTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getUnrecognizedMetadataPairRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"STRING");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleUnrecognizedMetadataPair2586); 

                	newLeafNode(otherlv_1, grammarAccess.getUnrecognizedMetadataPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1122:1: ( (lv_value_2_0= ruleValue ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1123:1: (lv_value_2_0= ruleValue )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1123:1: (lv_value_2_0= ruleValue )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1124:3: lv_value_2_0= ruleValue
            {
             
            	        newCompositeNode(grammarAccess.getUnrecognizedMetadataPairAccess().getValueValueParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleValue_in_ruleUnrecognizedMetadataPair2607);
            lv_value_2_0=ruleValue();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getUnrecognizedMetadataPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnrecognizedMetadataPair"


    // $ANTLR start "entryRuleStringArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1148:1: entryRuleStringArray returns [EObject current=null] : iv_ruleStringArray= ruleStringArray EOF ;
    public final EObject entryRuleStringArray() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStringArray = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1149:2: (iv_ruleStringArray= ruleStringArray EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1150:2: iv_ruleStringArray= ruleStringArray EOF
            {
             newCompositeNode(grammarAccess.getStringArrayRule()); 
            pushFollow(FOLLOW_ruleStringArray_in_entryRuleStringArray2643);
            iv_ruleStringArray=ruleStringArray();

            state._fsp--;

             current =iv_ruleStringArray; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleStringArray2653); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStringArray"


    // $ANTLR start "ruleStringArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1157:1: ruleStringArray returns [EObject current=null] : ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleStringLiteral ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleStringLiteral ) ) )* )? otherlv_5= ']' ) ;
    public final EObject ruleStringArray() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_value_2_0 = null;

        EObject lv_value_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1160:28: ( ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleStringLiteral ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleStringLiteral ) ) )* )? otherlv_5= ']' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1161:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleStringLiteral ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleStringLiteral ) ) )* )? otherlv_5= ']' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1161:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleStringLiteral ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleStringLiteral ) ) )* )? otherlv_5= ']' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1161:2: () otherlv_1= '[' ( ( (lv_value_2_0= ruleStringLiteral ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleStringLiteral ) ) )* )? otherlv_5= ']'
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1161:2: ()
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1162:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getStringArrayAccess().getJsonArrayAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,26,FOLLOW_26_in_ruleStringArray2699); 

                	newLeafNode(otherlv_1, grammarAccess.getStringArrayAccess().getLeftSquareBracketKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1171:1: ( ( (lv_value_2_0= ruleStringLiteral ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleStringLiteral ) ) )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_STRING||LA6_0==13||(LA6_0>=15 && LA6_0<=25)||(LA6_0>=28 && LA6_0<=31)) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1171:2: ( (lv_value_2_0= ruleStringLiteral ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleStringLiteral ) ) )*
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1171:2: ( (lv_value_2_0= ruleStringLiteral ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1172:1: (lv_value_2_0= ruleStringLiteral )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1172:1: (lv_value_2_0= ruleStringLiteral )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1173:3: lv_value_2_0= ruleStringLiteral
                    {
                     
                    	        newCompositeNode(grammarAccess.getStringArrayAccess().getValueStringLiteralParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleStringLiteral_in_ruleStringArray2721);
                    lv_value_2_0=ruleStringLiteral();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getStringArrayRule());
                    	        }
                           		add(
                           			current, 
                           			"value",
                            		lv_value_2_0, 
                            		"StringLiteral");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1189:2: (otherlv_3= ',' ( (lv_value_4_0= ruleStringLiteral ) ) )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==11) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1189:4: otherlv_3= ',' ( (lv_value_4_0= ruleStringLiteral ) )
                    	    {
                    	    otherlv_3=(Token)match(input,11,FOLLOW_11_in_ruleStringArray2734); 

                    	        	newLeafNode(otherlv_3, grammarAccess.getStringArrayAccess().getCommaKeyword_2_1_0());
                    	        
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1193:1: ( (lv_value_4_0= ruleStringLiteral ) )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1194:1: (lv_value_4_0= ruleStringLiteral )
                    	    {
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1194:1: (lv_value_4_0= ruleStringLiteral )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1195:3: lv_value_4_0= ruleStringLiteral
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getStringArrayAccess().getValueStringLiteralParserRuleCall_2_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleStringLiteral_in_ruleStringArray2755);
                    	    lv_value_4_0=ruleStringLiteral();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getStringArrayRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"value",
                    	            		lv_value_4_0, 
                    	            		"StringLiteral");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,27,FOLLOW_27_in_ruleStringArray2771); 

                	newLeafNode(otherlv_5, grammarAccess.getStringArrayAccess().getRightSquareBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStringArray"


    // $ANTLR start "entryRuleDependencyArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1223:1: entryRuleDependencyArray returns [EObject current=null] : iv_ruleDependencyArray= ruleDependencyArray EOF ;
    public final EObject entryRuleDependencyArray() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDependencyArray = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1224:2: (iv_ruleDependencyArray= ruleDependencyArray EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1225:2: iv_ruleDependencyArray= ruleDependencyArray EOF
            {
             newCompositeNode(grammarAccess.getDependencyArrayRule()); 
            pushFollow(FOLLOW_ruleDependencyArray_in_entryRuleDependencyArray2807);
            iv_ruleDependencyArray=ruleDependencyArray();

            state._fsp--;

             current =iv_ruleDependencyArray; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDependencyArray2817); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDependencyArray"


    // $ANTLR start "ruleDependencyArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1232:1: ruleDependencyArray returns [EObject current=null] : ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleDependencyObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleDependencyObject ) ) )* )? otherlv_5= ']' ) ;
    public final EObject ruleDependencyArray() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_value_2_0 = null;

        EObject lv_value_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1235:28: ( ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleDependencyObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleDependencyObject ) ) )* )? otherlv_5= ']' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1236:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleDependencyObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleDependencyObject ) ) )* )? otherlv_5= ']' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1236:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleDependencyObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleDependencyObject ) ) )* )? otherlv_5= ']' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1236:2: () otherlv_1= '[' ( ( (lv_value_2_0= ruleDependencyObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleDependencyObject ) ) )* )? otherlv_5= ']'
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1236:2: ()
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1237:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getDependencyArrayAccess().getJsonArrayAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,26,FOLLOW_26_in_ruleDependencyArray2863); 

                	newLeafNode(otherlv_1, grammarAccess.getDependencyArrayAccess().getLeftSquareBracketKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1246:1: ( ( (lv_value_2_0= ruleDependencyObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleDependencyObject ) ) )* )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==10) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1246:2: ( (lv_value_2_0= ruleDependencyObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleDependencyObject ) ) )*
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1246:2: ( (lv_value_2_0= ruleDependencyObject ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1247:1: (lv_value_2_0= ruleDependencyObject )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1247:1: (lv_value_2_0= ruleDependencyObject )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1248:3: lv_value_2_0= ruleDependencyObject
                    {
                     
                    	        newCompositeNode(grammarAccess.getDependencyArrayAccess().getValueDependencyObjectParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleDependencyObject_in_ruleDependencyArray2885);
                    lv_value_2_0=ruleDependencyObject();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getDependencyArrayRule());
                    	        }
                           		add(
                           			current, 
                           			"value",
                            		lv_value_2_0, 
                            		"DependencyObject");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1264:2: (otherlv_3= ',' ( (lv_value_4_0= ruleDependencyObject ) ) )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==11) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1264:4: otherlv_3= ',' ( (lv_value_4_0= ruleDependencyObject ) )
                    	    {
                    	    otherlv_3=(Token)match(input,11,FOLLOW_11_in_ruleDependencyArray2898); 

                    	        	newLeafNode(otherlv_3, grammarAccess.getDependencyArrayAccess().getCommaKeyword_2_1_0());
                    	        
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1268:1: ( (lv_value_4_0= ruleDependencyObject ) )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1269:1: (lv_value_4_0= ruleDependencyObject )
                    	    {
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1269:1: (lv_value_4_0= ruleDependencyObject )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1270:3: lv_value_4_0= ruleDependencyObject
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getDependencyArrayAccess().getValueDependencyObjectParserRuleCall_2_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDependencyObject_in_ruleDependencyArray2919);
                    	    lv_value_4_0=ruleDependencyObject();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getDependencyArrayRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"value",
                    	            		lv_value_4_0, 
                    	            		"DependencyObject");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,27,FOLLOW_27_in_ruleDependencyArray2935); 

                	newLeafNode(otherlv_5, grammarAccess.getDependencyArrayAccess().getRightSquareBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDependencyArray"


    // $ANTLR start "entryRuleDependencyObject"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1298:1: entryRuleDependencyObject returns [EObject current=null] : iv_ruleDependencyObject= ruleDependencyObject EOF ;
    public final EObject entryRuleDependencyObject() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDependencyObject = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1299:2: (iv_ruleDependencyObject= ruleDependencyObject EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1300:2: iv_ruleDependencyObject= ruleDependencyObject EOF
            {
             newCompositeNode(grammarAccess.getDependencyObjectRule()); 
            pushFollow(FOLLOW_ruleDependencyObject_in_entryRuleDependencyObject2971);
            iv_ruleDependencyObject=ruleDependencyObject();

            state._fsp--;

             current =iv_ruleDependencyObject; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDependencyObject2981); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDependencyObject"


    // $ANTLR start "ruleDependencyObject"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1307:1: ruleDependencyObject returns [EObject current=null] : this_JsonDependency_0= ruleJsonDependency ;
    public final EObject ruleDependencyObject() throws RecognitionException {
        EObject current = null;

        EObject this_JsonDependency_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1310:28: (this_JsonDependency_0= ruleJsonDependency )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1312:5: this_JsonDependency_0= ruleJsonDependency
            {
             
                    newCompositeNode(grammarAccess.getDependencyObjectAccess().getJsonDependencyParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleJsonDependency_in_ruleDependencyObject3027);
            this_JsonDependency_0=ruleJsonDependency();

            state._fsp--;

             
                    current = this_JsonDependency_0; 
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDependencyObject"


    // $ANTLR start "entryRuleJsonDependency"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1328:1: entryRuleJsonDependency returns [EObject current=null] : iv_ruleJsonDependency= ruleJsonDependency EOF ;
    public final EObject entryRuleJsonDependency() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonDependency = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1329:2: (iv_ruleJsonDependency= ruleJsonDependency EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1330:2: iv_ruleJsonDependency= ruleJsonDependency EOF
            {
             newCompositeNode(grammarAccess.getJsonDependencyRule()); 
            pushFollow(FOLLOW_ruleJsonDependency_in_entryRuleJsonDependency3061);
            iv_ruleJsonDependency=ruleJsonDependency();

            state._fsp--;

             current =iv_ruleJsonDependency; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonDependency3071); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonDependency"


    // $ANTLR start "ruleJsonDependency"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1337:1: ruleJsonDependency returns [EObject current=null] : (otherlv_0= '{' ( (lv_pairs_1_0= ruleDependencyPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleDependencyPair ) ) )* otherlv_4= '}' ) ;
    public final EObject ruleJsonDependency() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_pairs_1_0 = null;

        EObject lv_pairs_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1340:28: ( (otherlv_0= '{' ( (lv_pairs_1_0= ruleDependencyPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleDependencyPair ) ) )* otherlv_4= '}' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1341:1: (otherlv_0= '{' ( (lv_pairs_1_0= ruleDependencyPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleDependencyPair ) ) )* otherlv_4= '}' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1341:1: (otherlv_0= '{' ( (lv_pairs_1_0= ruleDependencyPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleDependencyPair ) ) )* otherlv_4= '}' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1341:3: otherlv_0= '{' ( (lv_pairs_1_0= ruleDependencyPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleDependencyPair ) ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,10,FOLLOW_10_in_ruleJsonDependency3108); 

                	newLeafNode(otherlv_0, grammarAccess.getJsonDependencyAccess().getLeftCurlyBracketKeyword_0());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1345:1: ( (lv_pairs_1_0= ruleDependencyPair ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1346:1: (lv_pairs_1_0= ruleDependencyPair )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1346:1: (lv_pairs_1_0= ruleDependencyPair )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1347:3: lv_pairs_1_0= ruleDependencyPair
            {
             
            	        newCompositeNode(grammarAccess.getJsonDependencyAccess().getPairsDependencyPairParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleDependencyPair_in_ruleJsonDependency3129);
            lv_pairs_1_0=ruleDependencyPair();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getJsonDependencyRule());
            	        }
                   		add(
                   			current, 
                   			"pairs",
                    		lv_pairs_1_0, 
                    		"DependencyPair");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1363:2: (otherlv_2= ',' ( (lv_pairs_3_0= ruleDependencyPair ) ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==11) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1363:4: otherlv_2= ',' ( (lv_pairs_3_0= ruleDependencyPair ) )
            	    {
            	    otherlv_2=(Token)match(input,11,FOLLOW_11_in_ruleJsonDependency3142); 

            	        	newLeafNode(otherlv_2, grammarAccess.getJsonDependencyAccess().getCommaKeyword_2_0());
            	        
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1367:1: ( (lv_pairs_3_0= ruleDependencyPair ) )
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1368:1: (lv_pairs_3_0= ruleDependencyPair )
            	    {
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1368:1: (lv_pairs_3_0= ruleDependencyPair )
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1369:3: lv_pairs_3_0= ruleDependencyPair
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getJsonDependencyAccess().getPairsDependencyPairParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleDependencyPair_in_ruleJsonDependency3163);
            	    lv_pairs_3_0=ruleDependencyPair();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getJsonDependencyRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"pairs",
            	            		lv_pairs_3_0, 
            	            		"DependencyPair");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleJsonDependency3177); 

                	newLeafNode(otherlv_4, grammarAccess.getJsonDependencyAccess().getRightCurlyBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonDependency"


    // $ANTLR start "entryRuleDependencyPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1397:1: entryRuleDependencyPair returns [EObject current=null] : iv_ruleDependencyPair= ruleDependencyPair EOF ;
    public final EObject entryRuleDependencyPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDependencyPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1398:2: (iv_ruleDependencyPair= ruleDependencyPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1399:2: iv_ruleDependencyPair= ruleDependencyPair EOF
            {
             newCompositeNode(grammarAccess.getDependencyPairRule()); 
            pushFollow(FOLLOW_ruleDependencyPair_in_entryRuleDependencyPair3213);
            iv_ruleDependencyPair=ruleDependencyPair();

            state._fsp--;

             current =iv_ruleDependencyPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDependencyPair3223); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDependencyPair"


    // $ANTLR start "ruleDependencyPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1406:1: ruleDependencyPair returns [EObject current=null] : (this_MetadataRefPair_0= ruleMetadataRefPair | this_VRPair_1= ruleVRPair | this_UnrecognizedRequirementPair_2= ruleUnrecognizedRequirementPair ) ;
    public final EObject ruleDependencyPair() throws RecognitionException {
        EObject current = null;

        EObject this_MetadataRefPair_0 = null;

        EObject this_VRPair_1 = null;

        EObject this_UnrecognizedRequirementPair_2 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1409:28: ( (this_MetadataRefPair_0= ruleMetadataRefPair | this_VRPair_1= ruleVRPair | this_UnrecognizedRequirementPair_2= ruleUnrecognizedRequirementPair ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1410:1: (this_MetadataRefPair_0= ruleMetadataRefPair | this_VRPair_1= ruleVRPair | this_UnrecognizedRequirementPair_2= ruleUnrecognizedRequirementPair )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1410:1: (this_MetadataRefPair_0= ruleMetadataRefPair | this_VRPair_1= ruleVRPair | this_UnrecognizedRequirementPair_2= ruleUnrecognizedRequirementPair )
            int alt10=3;
            switch ( input.LA(1) ) {
            case 18:
                {
                alt10=1;
                }
                break;
            case 28:
                {
                alt10=2;
                }
                break;
            case RULE_STRING:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 29:
            case 30:
            case 31:
                {
                alt10=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1411:5: this_MetadataRefPair_0= ruleMetadataRefPair
                    {
                     
                            newCompositeNode(grammarAccess.getDependencyPairAccess().getMetadataRefPairParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleMetadataRefPair_in_ruleDependencyPair3270);
                    this_MetadataRefPair_0=ruleMetadataRefPair();

                    state._fsp--;

                     
                            current = this_MetadataRefPair_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1421:5: this_VRPair_1= ruleVRPair
                    {
                     
                            newCompositeNode(grammarAccess.getDependencyPairAccess().getVRPairParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleVRPair_in_ruleDependencyPair3297);
                    this_VRPair_1=ruleVRPair();

                    state._fsp--;

                     
                            current = this_VRPair_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1431:5: this_UnrecognizedRequirementPair_2= ruleUnrecognizedRequirementPair
                    {
                     
                            newCompositeNode(grammarAccess.getDependencyPairAccess().getUnrecognizedRequirementPairParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleUnrecognizedRequirementPair_in_ruleDependencyPair3324);
                    this_UnrecognizedRequirementPair_2=ruleUnrecognizedRequirementPair();

                    state._fsp--;

                     
                            current = this_UnrecognizedRequirementPair_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDependencyPair"


    // $ANTLR start "entryRuleMetadataRefPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1447:1: entryRuleMetadataRefPair returns [EObject current=null] : iv_ruleMetadataRefPair= ruleMetadataRefPair EOF ;
    public final EObject entryRuleMetadataRefPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetadataRefPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1448:2: (iv_ruleMetadataRefPair= ruleMetadataRefPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1449:2: iv_ruleMetadataRefPair= ruleMetadataRefPair EOF
            {
             newCompositeNode(grammarAccess.getMetadataRefPairRule()); 
            pushFollow(FOLLOW_ruleMetadataRefPair_in_entryRuleMetadataRefPair3359);
            iv_ruleMetadataRefPair=ruleMetadataRefPair();

            state._fsp--;

             current =iv_ruleMetadataRefPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMetadataRefPair3369); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMetadataRefPair"


    // $ANTLR start "ruleMetadataRefPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1456:1: ruleMetadataRefPair returns [EObject current=null] : ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (otherlv_2= RULE_STRING ) ) ) ;
    public final EObject ruleMetadataRefPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1459:28: ( ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (otherlv_2= RULE_STRING ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1460:1: ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (otherlv_2= RULE_STRING ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1460:1: ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (otherlv_2= RULE_STRING ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1460:2: ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (otherlv_2= RULE_STRING ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1460:2: ( (lv_name_0_0= '\"name\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1461:1: (lv_name_0_0= '\"name\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1461:1: (lv_name_0_0= '\"name\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1462:3: lv_name_0_0= '\"name\"'
            {
            lv_name_0_0=(Token)match(input,18,FOLLOW_18_in_ruleMetadataRefPair3412); 

                    newLeafNode(lv_name_0_0, grammarAccess.getMetadataRefPairAccess().getNameNameKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getMetadataRefPairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"name\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleMetadataRefPair3437); 

                	newLeafNode(otherlv_1, grammarAccess.getMetadataRefPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1479:1: ( (otherlv_2= RULE_STRING ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1480:1: (otherlv_2= RULE_STRING )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1480:1: (otherlv_2= RULE_STRING )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1481:3: otherlv_2= RULE_STRING
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getMetadataRefPairRule());
            	        }
                    
            otherlv_2=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleMetadataRefPair3457); 

            		newLeafNode(otherlv_2, grammarAccess.getMetadataRefPairAccess().getRefJsonMetadataCrossReference_2_0()); 
            	

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMetadataRefPair"


    // $ANTLR start "entryRuleRequirementArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1500:1: entryRuleRequirementArray returns [EObject current=null] : iv_ruleRequirementArray= ruleRequirementArray EOF ;
    public final EObject entryRuleRequirementArray() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequirementArray = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1501:2: (iv_ruleRequirementArray= ruleRequirementArray EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1502:2: iv_ruleRequirementArray= ruleRequirementArray EOF
            {
             newCompositeNode(grammarAccess.getRequirementArrayRule()); 
            pushFollow(FOLLOW_ruleRequirementArray_in_entryRuleRequirementArray3493);
            iv_ruleRequirementArray=ruleRequirementArray();

            state._fsp--;

             current =iv_ruleRequirementArray; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRequirementArray3503); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRequirementArray"


    // $ANTLR start "ruleRequirementArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1509:1: ruleRequirementArray returns [EObject current=null] : ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleRequirementObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleRequirementObject ) ) )* )? otherlv_5= ']' ) ;
    public final EObject ruleRequirementArray() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_value_2_0 = null;

        EObject lv_value_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1512:28: ( ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleRequirementObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleRequirementObject ) ) )* )? otherlv_5= ']' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1513:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleRequirementObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleRequirementObject ) ) )* )? otherlv_5= ']' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1513:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleRequirementObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleRequirementObject ) ) )* )? otherlv_5= ']' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1513:2: () otherlv_1= '[' ( ( (lv_value_2_0= ruleRequirementObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleRequirementObject ) ) )* )? otherlv_5= ']'
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1513:2: ()
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1514:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getRequirementArrayAccess().getJsonArrayAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,26,FOLLOW_26_in_ruleRequirementArray3549); 

                	newLeafNode(otherlv_1, grammarAccess.getRequirementArrayAccess().getLeftSquareBracketKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1523:1: ( ( (lv_value_2_0= ruleRequirementObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleRequirementObject ) ) )* )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==10) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1523:2: ( (lv_value_2_0= ruleRequirementObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleRequirementObject ) ) )*
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1523:2: ( (lv_value_2_0= ruleRequirementObject ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1524:1: (lv_value_2_0= ruleRequirementObject )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1524:1: (lv_value_2_0= ruleRequirementObject )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1525:3: lv_value_2_0= ruleRequirementObject
                    {
                     
                    	        newCompositeNode(grammarAccess.getRequirementArrayAccess().getValueRequirementObjectParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleRequirementObject_in_ruleRequirementArray3571);
                    lv_value_2_0=ruleRequirementObject();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getRequirementArrayRule());
                    	        }
                           		add(
                           			current, 
                           			"value",
                            		lv_value_2_0, 
                            		"RequirementObject");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1541:2: (otherlv_3= ',' ( (lv_value_4_0= ruleRequirementObject ) ) )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0==11) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1541:4: otherlv_3= ',' ( (lv_value_4_0= ruleRequirementObject ) )
                    	    {
                    	    otherlv_3=(Token)match(input,11,FOLLOW_11_in_ruleRequirementArray3584); 

                    	        	newLeafNode(otherlv_3, grammarAccess.getRequirementArrayAccess().getCommaKeyword_2_1_0());
                    	        
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1545:1: ( (lv_value_4_0= ruleRequirementObject ) )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1546:1: (lv_value_4_0= ruleRequirementObject )
                    	    {
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1546:1: (lv_value_4_0= ruleRequirementObject )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1547:3: lv_value_4_0= ruleRequirementObject
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getRequirementArrayAccess().getValueRequirementObjectParserRuleCall_2_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleRequirementObject_in_ruleRequirementArray3605);
                    	    lv_value_4_0=ruleRequirementObject();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getRequirementArrayRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"value",
                    	            		lv_value_4_0, 
                    	            		"RequirementObject");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,27,FOLLOW_27_in_ruleRequirementArray3621); 

                	newLeafNode(otherlv_5, grammarAccess.getRequirementArrayAccess().getRightSquareBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRequirementArray"


    // $ANTLR start "entryRuleRequirementObject"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1575:1: entryRuleRequirementObject returns [EObject current=null] : iv_ruleRequirementObject= ruleRequirementObject EOF ;
    public final EObject entryRuleRequirementObject() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequirementObject = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1576:2: (iv_ruleRequirementObject= ruleRequirementObject EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1577:2: iv_ruleRequirementObject= ruleRequirementObject EOF
            {
             newCompositeNode(grammarAccess.getRequirementObjectRule()); 
            pushFollow(FOLLOW_ruleRequirementObject_in_entryRuleRequirementObject3657);
            iv_ruleRequirementObject=ruleRequirementObject();

            state._fsp--;

             current =iv_ruleRequirementObject; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRequirementObject3667); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRequirementObject"


    // $ANTLR start "ruleRequirementObject"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1584:1: ruleRequirementObject returns [EObject current=null] : this_JsonRequirement_0= ruleJsonRequirement ;
    public final EObject ruleRequirementObject() throws RecognitionException {
        EObject current = null;

        EObject this_JsonRequirement_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1587:28: (this_JsonRequirement_0= ruleJsonRequirement )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1589:5: this_JsonRequirement_0= ruleJsonRequirement
            {
             
                    newCompositeNode(grammarAccess.getRequirementObjectAccess().getJsonRequirementParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleJsonRequirement_in_ruleRequirementObject3713);
            this_JsonRequirement_0=ruleJsonRequirement();

            state._fsp--;

             
                    current = this_JsonRequirement_0; 
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRequirementObject"


    // $ANTLR start "entryRuleJsonRequirement"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1605:1: entryRuleJsonRequirement returns [EObject current=null] : iv_ruleJsonRequirement= ruleJsonRequirement EOF ;
    public final EObject entryRuleJsonRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonRequirement = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1606:2: (iv_ruleJsonRequirement= ruleJsonRequirement EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1607:2: iv_ruleJsonRequirement= ruleJsonRequirement EOF
            {
             newCompositeNode(grammarAccess.getJsonRequirementRule()); 
            pushFollow(FOLLOW_ruleJsonRequirement_in_entryRuleJsonRequirement3747);
            iv_ruleJsonRequirement=ruleJsonRequirement();

            state._fsp--;

             current =iv_ruleJsonRequirement; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonRequirement3757); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonRequirement"


    // $ANTLR start "ruleJsonRequirement"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1614:1: ruleJsonRequirement returns [EObject current=null] : (otherlv_0= '{' ( (lv_pairs_1_0= ruleRequirementPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleRequirementPair ) ) )* otherlv_4= '}' ) ;
    public final EObject ruleJsonRequirement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_pairs_1_0 = null;

        EObject lv_pairs_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1617:28: ( (otherlv_0= '{' ( (lv_pairs_1_0= ruleRequirementPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleRequirementPair ) ) )* otherlv_4= '}' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1618:1: (otherlv_0= '{' ( (lv_pairs_1_0= ruleRequirementPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleRequirementPair ) ) )* otherlv_4= '}' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1618:1: (otherlv_0= '{' ( (lv_pairs_1_0= ruleRequirementPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleRequirementPair ) ) )* otherlv_4= '}' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1618:3: otherlv_0= '{' ( (lv_pairs_1_0= ruleRequirementPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleRequirementPair ) ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,10,FOLLOW_10_in_ruleJsonRequirement3794); 

                	newLeafNode(otherlv_0, grammarAccess.getJsonRequirementAccess().getLeftCurlyBracketKeyword_0());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1622:1: ( (lv_pairs_1_0= ruleRequirementPair ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1623:1: (lv_pairs_1_0= ruleRequirementPair )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1623:1: (lv_pairs_1_0= ruleRequirementPair )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1624:3: lv_pairs_1_0= ruleRequirementPair
            {
             
            	        newCompositeNode(grammarAccess.getJsonRequirementAccess().getPairsRequirementPairParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleRequirementPair_in_ruleJsonRequirement3815);
            lv_pairs_1_0=ruleRequirementPair();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getJsonRequirementRule());
            	        }
                   		add(
                   			current, 
                   			"pairs",
                    		lv_pairs_1_0, 
                    		"RequirementPair");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1640:2: (otherlv_2= ',' ( (lv_pairs_3_0= ruleRequirementPair ) ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==11) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1640:4: otherlv_2= ',' ( (lv_pairs_3_0= ruleRequirementPair ) )
            	    {
            	    otherlv_2=(Token)match(input,11,FOLLOW_11_in_ruleJsonRequirement3828); 

            	        	newLeafNode(otherlv_2, grammarAccess.getJsonRequirementAccess().getCommaKeyword_2_0());
            	        
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1644:1: ( (lv_pairs_3_0= ruleRequirementPair ) )
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1645:1: (lv_pairs_3_0= ruleRequirementPair )
            	    {
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1645:1: (lv_pairs_3_0= ruleRequirementPair )
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1646:3: lv_pairs_3_0= ruleRequirementPair
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getJsonRequirementAccess().getPairsRequirementPairParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleRequirementPair_in_ruleJsonRequirement3849);
            	    lv_pairs_3_0=ruleRequirementPair();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getJsonRequirementRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"pairs",
            	            		lv_pairs_3_0, 
            	            		"RequirementPair");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleJsonRequirement3863); 

                	newLeafNode(otherlv_4, grammarAccess.getJsonRequirementAccess().getRightCurlyBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonRequirement"


    // $ANTLR start "entryRuleRequirementPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1674:1: entryRuleRequirementPair returns [EObject current=null] : iv_ruleRequirementPair= ruleRequirementPair EOF ;
    public final EObject entryRuleRequirementPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequirementPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1675:2: (iv_ruleRequirementPair= ruleRequirementPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1676:2: iv_ruleRequirementPair= ruleRequirementPair EOF
            {
             newCompositeNode(grammarAccess.getRequirementPairRule()); 
            pushFollow(FOLLOW_ruleRequirementPair_in_entryRuleRequirementPair3899);
            iv_ruleRequirementPair=ruleRequirementPair();

            state._fsp--;

             current =iv_ruleRequirementPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRequirementPair3909); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRequirementPair"


    // $ANTLR start "ruleRequirementPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1683:1: ruleRequirementPair returns [EObject current=null] : ( ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementName ) ) ) | this_VRPair_3= ruleVRPair | this_UnrecognizedRequirementPair_4= ruleUnrecognizedRequirementPair ) ;
    public final EObject ruleRequirementPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;

        EObject this_VRPair_3 = null;

        EObject this_UnrecognizedRequirementPair_4 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1686:28: ( ( ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementName ) ) ) | this_VRPair_3= ruleVRPair | this_UnrecognizedRequirementPair_4= ruleUnrecognizedRequirementPair ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1687:1: ( ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementName ) ) ) | this_VRPair_3= ruleVRPair | this_UnrecognizedRequirementPair_4= ruleUnrecognizedRequirementPair )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1687:1: ( ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementName ) ) ) | this_VRPair_3= ruleVRPair | this_UnrecognizedRequirementPair_4= ruleUnrecognizedRequirementPair )
            int alt14=3;
            switch ( input.LA(1) ) {
            case 18:
                {
                alt14=1;
                }
                break;
            case 28:
                {
                alt14=2;
                }
                break;
            case RULE_STRING:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 29:
            case 30:
            case 31:
                {
                alt14=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1687:2: ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementName ) ) )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1687:2: ( ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementName ) ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1687:3: ( (lv_name_0_0= '\"name\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleRequirementName ) )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1687:3: ( (lv_name_0_0= '\"name\"' ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1688:1: (lv_name_0_0= '\"name\"' )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1688:1: (lv_name_0_0= '\"name\"' )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1689:3: lv_name_0_0= '\"name\"'
                    {
                    lv_name_0_0=(Token)match(input,18,FOLLOW_18_in_ruleRequirementPair3953); 

                            newLeafNode(lv_name_0_0, grammarAccess.getRequirementPairAccess().getNameNameKeyword_0_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getRequirementPairRule());
                    	        }
                           		setWithLastConsumed(current, "name", lv_name_0_0, "\"name\"");
                    	    

                    }


                    }

                    otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleRequirementPair3978); 

                        	newLeafNode(otherlv_1, grammarAccess.getRequirementPairAccess().getColonKeyword_0_1());
                        
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1706:1: ( (lv_value_2_0= ruleRequirementName ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1707:1: (lv_value_2_0= ruleRequirementName )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1707:1: (lv_value_2_0= ruleRequirementName )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1708:3: lv_value_2_0= ruleRequirementName
                    {
                     
                    	        newCompositeNode(grammarAccess.getRequirementPairAccess().getValueRequirementNameParserRuleCall_0_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleRequirementName_in_ruleRequirementPair3999);
                    lv_value_2_0=ruleRequirementName();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getRequirementPairRule());
                    	        }
                           		set(
                           			current, 
                           			"value",
                            		lv_value_2_0, 
                            		"RequirementName");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1726:5: this_VRPair_3= ruleVRPair
                    {
                     
                            newCompositeNode(grammarAccess.getRequirementPairAccess().getVRPairParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleVRPair_in_ruleRequirementPair4028);
                    this_VRPair_3=ruleVRPair();

                    state._fsp--;

                     
                            current = this_VRPair_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1736:5: this_UnrecognizedRequirementPair_4= ruleUnrecognizedRequirementPair
                    {
                     
                            newCompositeNode(grammarAccess.getRequirementPairAccess().getUnrecognizedRequirementPairParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleUnrecognizedRequirementPair_in_ruleRequirementPair4055);
                    this_UnrecognizedRequirementPair_4=ruleUnrecognizedRequirementPair();

                    state._fsp--;

                     
                            current = this_UnrecognizedRequirementPair_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRequirementPair"


    // $ANTLR start "entryRuleRequirementName"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1752:1: entryRuleRequirementName returns [EObject current=null] : iv_ruleRequirementName= ruleRequirementName EOF ;
    public final EObject entryRuleRequirementName() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequirementName = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1753:2: (iv_ruleRequirementName= ruleRequirementName EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1754:2: iv_ruleRequirementName= ruleRequirementName EOF
            {
             newCompositeNode(grammarAccess.getRequirementNameRule()); 
            pushFollow(FOLLOW_ruleRequirementName_in_entryRuleRequirementName4090);
            iv_ruleRequirementName=ruleRequirementName();

            state._fsp--;

             current =iv_ruleRequirementName; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRequirementName4100); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRequirementName"


    // $ANTLR start "ruleRequirementName"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1761:1: ruleRequirementName returns [EObject current=null] : this_RequirementNameValue_0= ruleRequirementNameValue ;
    public final EObject ruleRequirementName() throws RecognitionException {
        EObject current = null;

        EObject this_RequirementNameValue_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1764:28: (this_RequirementNameValue_0= ruleRequirementNameValue )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1766:5: this_RequirementNameValue_0= ruleRequirementNameValue
            {
             
                    newCompositeNode(grammarAccess.getRequirementNameAccess().getRequirementNameValueParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleRequirementNameValue_in_ruleRequirementName4146);
            this_RequirementNameValue_0=ruleRequirementNameValue();

            state._fsp--;

             
                    current = this_RequirementNameValue_0; 
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRequirementName"


    // $ANTLR start "entryRuleRequirementNameValue"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1782:1: entryRuleRequirementNameValue returns [EObject current=null] : iv_ruleRequirementNameValue= ruleRequirementNameValue EOF ;
    public final EObject entryRuleRequirementNameValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequirementNameValue = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1783:2: (iv_ruleRequirementNameValue= ruleRequirementNameValue EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1784:2: iv_ruleRequirementNameValue= ruleRequirementNameValue EOF
            {
             newCompositeNode(grammarAccess.getRequirementNameValueRule()); 
            pushFollow(FOLLOW_ruleRequirementNameValue_in_entryRuleRequirementNameValue4180);
            iv_ruleRequirementNameValue=ruleRequirementNameValue();

            state._fsp--;

             current =iv_ruleRequirementNameValue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRequirementNameValue4190); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRequirementNameValue"


    // $ANTLR start "ruleRequirementNameValue"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1791:1: ruleRequirementNameValue returns [EObject current=null] : ( (lv_value_0_0= RULE_STRING ) ) ;
    public final EObject ruleRequirementNameValue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1794:28: ( ( (lv_value_0_0= RULE_STRING ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1795:1: ( (lv_value_0_0= RULE_STRING ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1795:1: ( (lv_value_0_0= RULE_STRING ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1796:1: (lv_value_0_0= RULE_STRING )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1796:1: (lv_value_0_0= RULE_STRING )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1797:3: lv_value_0_0= RULE_STRING
            {
            lv_value_0_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleRequirementNameValue4231); 

            			newLeafNode(lv_value_0_0, grammarAccess.getRequirementNameValueAccess().getValueSTRINGTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getRequirementNameValueRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"STRING");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRequirementNameValue"


    // $ANTLR start "entryRuleVRPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1821:1: entryRuleVRPair returns [EObject current=null] : iv_ruleVRPair= ruleVRPair EOF ;
    public final EObject entryRuleVRPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVRPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1822:2: (iv_ruleVRPair= ruleVRPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1823:2: iv_ruleVRPair= ruleVRPair EOF
            {
             newCompositeNode(grammarAccess.getVRPairRule()); 
            pushFollow(FOLLOW_ruleVRPair_in_entryRuleVRPair4271);
            iv_ruleVRPair=ruleVRPair();

            state._fsp--;

             current =iv_ruleVRPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVRPair4281); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVRPair"


    // $ANTLR start "ruleVRPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1830:1: ruleVRPair returns [EObject current=null] : ( ( (lv_name_0_0= '\"version_requirement\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleVersionRange ) ) ) ;
    public final EObject ruleVRPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1833:28: ( ( ( (lv_name_0_0= '\"version_requirement\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleVersionRange ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1834:1: ( ( (lv_name_0_0= '\"version_requirement\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleVersionRange ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1834:1: ( ( (lv_name_0_0= '\"version_requirement\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleVersionRange ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1834:2: ( (lv_name_0_0= '\"version_requirement\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleVersionRange ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1834:2: ( (lv_name_0_0= '\"version_requirement\"' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1835:1: (lv_name_0_0= '\"version_requirement\"' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1835:1: (lv_name_0_0= '\"version_requirement\"' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1836:3: lv_name_0_0= '\"version_requirement\"'
            {
            lv_name_0_0=(Token)match(input,28,FOLLOW_28_in_ruleVRPair4324); 

                    newLeafNode(lv_name_0_0, grammarAccess.getVRPairAccess().getNameVersion_requirementKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getVRPairRule());
            	        }
                   		setWithLastConsumed(current, "name", lv_name_0_0, "\"version_requirement\"");
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleVRPair4349); 

                	newLeafNode(otherlv_1, grammarAccess.getVRPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1853:1: ( (lv_value_2_0= ruleVersionRange ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1854:1: (lv_value_2_0= ruleVersionRange )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1854:1: (lv_value_2_0= ruleVersionRange )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1855:3: lv_value_2_0= ruleVersionRange
            {
             
            	        newCompositeNode(grammarAccess.getVRPairAccess().getValueVersionRangeParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleVersionRange_in_ruleVRPair4370);
            lv_value_2_0=ruleVersionRange();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getVRPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"VersionRange");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVRPair"


    // $ANTLR start "entryRuleUnrecognizedRequirementPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1879:1: entryRuleUnrecognizedRequirementPair returns [EObject current=null] : iv_ruleUnrecognizedRequirementPair= ruleUnrecognizedRequirementPair EOF ;
    public final EObject entryRuleUnrecognizedRequirementPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnrecognizedRequirementPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1880:2: (iv_ruleUnrecognizedRequirementPair= ruleUnrecognizedRequirementPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1881:2: iv_ruleUnrecognizedRequirementPair= ruleUnrecognizedRequirementPair EOF
            {
             newCompositeNode(grammarAccess.getUnrecognizedRequirementPairRule()); 
            pushFollow(FOLLOW_ruleUnrecognizedRequirementPair_in_entryRuleUnrecognizedRequirementPair4406);
            iv_ruleUnrecognizedRequirementPair=ruleUnrecognizedRequirementPair();

            state._fsp--;

             current =iv_ruleUnrecognizedRequirementPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnrecognizedRequirementPair4416); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnrecognizedRequirementPair"


    // $ANTLR start "ruleUnrecognizedRequirementPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1888:1: ruleUnrecognizedRequirementPair returns [EObject current=null] : ( ( (lv_name_0_0= ruleQ_ReqUnknownKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleUnrecognizedRequirementPair() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1891:28: ( ( ( (lv_name_0_0= ruleQ_ReqUnknownKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1892:1: ( ( (lv_name_0_0= ruleQ_ReqUnknownKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1892:1: ( ( (lv_name_0_0= ruleQ_ReqUnknownKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1892:2: ( (lv_name_0_0= ruleQ_ReqUnknownKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1892:2: ( (lv_name_0_0= ruleQ_ReqUnknownKey ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1893:1: (lv_name_0_0= ruleQ_ReqUnknownKey )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1893:1: (lv_name_0_0= ruleQ_ReqUnknownKey )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1894:3: lv_name_0_0= ruleQ_ReqUnknownKey
            {
             
            	        newCompositeNode(grammarAccess.getUnrecognizedRequirementPairAccess().getNameQ_ReqUnknownKeyParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleQ_ReqUnknownKey_in_ruleUnrecognizedRequirementPair4462);
            lv_name_0_0=ruleQ_ReqUnknownKey();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getUnrecognizedRequirementPairRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"Q_ReqUnknownKey");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleUnrecognizedRequirementPair4474); 

                	newLeafNode(otherlv_1, grammarAccess.getUnrecognizedRequirementPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1914:1: ( (lv_value_2_0= ruleValue ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1915:1: (lv_value_2_0= ruleValue )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1915:1: (lv_value_2_0= ruleValue )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1916:3: lv_value_2_0= ruleValue
            {
             
            	        newCompositeNode(grammarAccess.getUnrecognizedRequirementPairAccess().getValueValueParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleValue_in_ruleUnrecognizedRequirementPair4495);
            lv_value_2_0=ruleValue();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getUnrecognizedRequirementPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnrecognizedRequirementPair"


    // $ANTLR start "entryRuleVersionRange"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1940:1: entryRuleVersionRange returns [EObject current=null] : iv_ruleVersionRange= ruleVersionRange EOF ;
    public final EObject entryRuleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersionRange = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1941:2: (iv_ruleVersionRange= ruleVersionRange EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1942:2: iv_ruleVersionRange= ruleVersionRange EOF
            {
             newCompositeNode(grammarAccess.getVersionRangeRule()); 
            pushFollow(FOLLOW_ruleVersionRange_in_entryRuleVersionRange4531);
            iv_ruleVersionRange=ruleVersionRange();

            state._fsp--;

             current =iv_ruleVersionRange; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVersionRange4541); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVersionRange"


    // $ANTLR start "ruleVersionRange"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1949:1: ruleVersionRange returns [EObject current=null] : this_JsonVersionRange_0= ruleJsonVersionRange ;
    public final EObject ruleVersionRange() throws RecognitionException {
        EObject current = null;

        EObject this_JsonVersionRange_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1952:28: (this_JsonVersionRange_0= ruleJsonVersionRange )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1954:5: this_JsonVersionRange_0= ruleJsonVersionRange
            {
             
                    newCompositeNode(grammarAccess.getVersionRangeAccess().getJsonVersionRangeParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleJsonVersionRange_in_ruleVersionRange4587);
            this_JsonVersionRange_0=ruleJsonVersionRange();

            state._fsp--;

             
                    current = this_JsonVersionRange_0; 
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersionRange"


    // $ANTLR start "entryRuleJsonVersionRange"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1970:1: entryRuleJsonVersionRange returns [EObject current=null] : iv_ruleJsonVersionRange= ruleJsonVersionRange EOF ;
    public final EObject entryRuleJsonVersionRange() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonVersionRange = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1971:2: (iv_ruleJsonVersionRange= ruleJsonVersionRange EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1972:2: iv_ruleJsonVersionRange= ruleJsonVersionRange EOF
            {
             newCompositeNode(grammarAccess.getJsonVersionRangeRule()); 
            pushFollow(FOLLOW_ruleJsonVersionRange_in_entryRuleJsonVersionRange4621);
            iv_ruleJsonVersionRange=ruleJsonVersionRange();

            state._fsp--;

             current =iv_ruleJsonVersionRange; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonVersionRange4631); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonVersionRange"


    // $ANTLR start "ruleJsonVersionRange"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1979:1: ruleJsonVersionRange returns [EObject current=null] : ( (lv_value_0_0= RULE_STRING ) ) ;
    public final EObject ruleJsonVersionRange() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1982:28: ( ( (lv_value_0_0= RULE_STRING ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1983:1: ( (lv_value_0_0= RULE_STRING ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1983:1: ( (lv_value_0_0= RULE_STRING ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1984:1: (lv_value_0_0= RULE_STRING )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1984:1: (lv_value_0_0= RULE_STRING )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:1985:3: lv_value_0_0= RULE_STRING
            {
            lv_value_0_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleJsonVersionRange4672); 

            			newLeafNode(lv_value_0_0, grammarAccess.getJsonVersionRangeAccess().getValueSTRINGTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getJsonVersionRangeRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"STRING");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonVersionRange"


    // $ANTLR start "entryRuleModuleName"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2009:1: entryRuleModuleName returns [EObject current=null] : iv_ruleModuleName= ruleModuleName EOF ;
    public final EObject entryRuleModuleName() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModuleName = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2010:2: (iv_ruleModuleName= ruleModuleName EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2011:2: iv_ruleModuleName= ruleModuleName EOF
            {
             newCompositeNode(grammarAccess.getModuleNameRule()); 
            pushFollow(FOLLOW_ruleModuleName_in_entryRuleModuleName4712);
            iv_ruleModuleName=ruleModuleName();

            state._fsp--;

             current =iv_ruleModuleName; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleModuleName4722); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModuleName"


    // $ANTLR start "ruleModuleName"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2018:1: ruleModuleName returns [EObject current=null] : this_JsonModuleName_0= ruleJsonModuleName ;
    public final EObject ruleModuleName() throws RecognitionException {
        EObject current = null;

        EObject this_JsonModuleName_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2021:28: (this_JsonModuleName_0= ruleJsonModuleName )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2023:5: this_JsonModuleName_0= ruleJsonModuleName
            {
             
                    newCompositeNode(grammarAccess.getModuleNameAccess().getJsonModuleNameParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleJsonModuleName_in_ruleModuleName4768);
            this_JsonModuleName_0=ruleJsonModuleName();

            state._fsp--;

             
                    current = this_JsonModuleName_0; 
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModuleName"


    // $ANTLR start "entryRuleJsonModuleName"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2039:1: entryRuleJsonModuleName returns [EObject current=null] : iv_ruleJsonModuleName= ruleJsonModuleName EOF ;
    public final EObject entryRuleJsonModuleName() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonModuleName = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2040:2: (iv_ruleJsonModuleName= ruleJsonModuleName EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2041:2: iv_ruleJsonModuleName= ruleJsonModuleName EOF
            {
             newCompositeNode(grammarAccess.getJsonModuleNameRule()); 
            pushFollow(FOLLOW_ruleJsonModuleName_in_entryRuleJsonModuleName4802);
            iv_ruleJsonModuleName=ruleJsonModuleName();

            state._fsp--;

             current =iv_ruleJsonModuleName; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonModuleName4812); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonModuleName"


    // $ANTLR start "ruleJsonModuleName"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2048:1: ruleJsonModuleName returns [EObject current=null] : ( (lv_value_0_0= RULE_STRING ) ) ;
    public final EObject ruleJsonModuleName() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2051:28: ( ( (lv_value_0_0= RULE_STRING ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2052:1: ( (lv_value_0_0= RULE_STRING ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2052:1: ( (lv_value_0_0= RULE_STRING ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2053:1: (lv_value_0_0= RULE_STRING )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2053:1: (lv_value_0_0= RULE_STRING )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2054:3: lv_value_0_0= RULE_STRING
            {
            lv_value_0_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleJsonModuleName4853); 

            			newLeafNode(lv_value_0_0, grammarAccess.getJsonModuleNameAccess().getValueSTRINGTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getJsonModuleNameRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"STRING");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonModuleName"


    // $ANTLR start "entryRuleTagArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2078:1: entryRuleTagArray returns [EObject current=null] : iv_ruleTagArray= ruleTagArray EOF ;
    public final EObject entryRuleTagArray() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTagArray = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2079:2: (iv_ruleTagArray= ruleTagArray EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2080:2: iv_ruleTagArray= ruleTagArray EOF
            {
             newCompositeNode(grammarAccess.getTagArrayRule()); 
            pushFollow(FOLLOW_ruleTagArray_in_entryRuleTagArray4893);
            iv_ruleTagArray=ruleTagArray();

            state._fsp--;

             current =iv_ruleTagArray; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTagArray4903); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTagArray"


    // $ANTLR start "ruleTagArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2087:1: ruleTagArray returns [EObject current=null] : ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleTag ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleTag ) ) )* )? otherlv_5= ']' ) ;
    public final EObject ruleTagArray() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_value_2_0 = null;

        EObject lv_value_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2090:28: ( ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleTag ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleTag ) ) )* )? otherlv_5= ']' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2091:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleTag ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleTag ) ) )* )? otherlv_5= ']' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2091:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleTag ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleTag ) ) )* )? otherlv_5= ']' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2091:2: () otherlv_1= '[' ( ( (lv_value_2_0= ruleTag ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleTag ) ) )* )? otherlv_5= ']'
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2091:2: ()
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2092:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getTagArrayAccess().getJsonArrayAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,26,FOLLOW_26_in_ruleTagArray4949); 

                	newLeafNode(otherlv_1, grammarAccess.getTagArrayAccess().getLeftSquareBracketKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2101:1: ( ( (lv_value_2_0= ruleTag ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleTag ) ) )* )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_STRING||LA16_0==13||(LA16_0>=15 && LA16_0<=25)||(LA16_0>=28 && LA16_0<=31)) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2101:2: ( (lv_value_2_0= ruleTag ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleTag ) ) )*
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2101:2: ( (lv_value_2_0= ruleTag ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2102:1: (lv_value_2_0= ruleTag )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2102:1: (lv_value_2_0= ruleTag )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2103:3: lv_value_2_0= ruleTag
                    {
                     
                    	        newCompositeNode(grammarAccess.getTagArrayAccess().getValueTagParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleTag_in_ruleTagArray4971);
                    lv_value_2_0=ruleTag();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getTagArrayRule());
                    	        }
                           		add(
                           			current, 
                           			"value",
                            		lv_value_2_0, 
                            		"Tag");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2119:2: (otherlv_3= ',' ( (lv_value_4_0= ruleTag ) ) )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0==11) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2119:4: otherlv_3= ',' ( (lv_value_4_0= ruleTag ) )
                    	    {
                    	    otherlv_3=(Token)match(input,11,FOLLOW_11_in_ruleTagArray4984); 

                    	        	newLeafNode(otherlv_3, grammarAccess.getTagArrayAccess().getCommaKeyword_2_1_0());
                    	        
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2123:1: ( (lv_value_4_0= ruleTag ) )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2124:1: (lv_value_4_0= ruleTag )
                    	    {
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2124:1: (lv_value_4_0= ruleTag )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2125:3: lv_value_4_0= ruleTag
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getTagArrayAccess().getValueTagParserRuleCall_2_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleTag_in_ruleTagArray5005);
                    	    lv_value_4_0=ruleTag();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getTagArrayRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"value",
                    	            		lv_value_4_0, 
                    	            		"Tag");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,27,FOLLOW_27_in_ruleTagArray5021); 

                	newLeafNode(otherlv_5, grammarAccess.getTagArrayAccess().getRightSquareBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTagArray"


    // $ANTLR start "entryRuleTag"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2153:1: entryRuleTag returns [EObject current=null] : iv_ruleTag= ruleTag EOF ;
    public final EObject entryRuleTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTag = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2154:2: (iv_ruleTag= ruleTag EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2155:2: iv_ruleTag= ruleTag EOF
            {
             newCompositeNode(grammarAccess.getTagRule()); 
            pushFollow(FOLLOW_ruleTag_in_entryRuleTag5057);
            iv_ruleTag=ruleTag();

            state._fsp--;

             current =iv_ruleTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTag5067); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTag"


    // $ANTLR start "ruleTag"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2162:1: ruleTag returns [EObject current=null] : this_JsonTag_0= ruleJsonTag ;
    public final EObject ruleTag() throws RecognitionException {
        EObject current = null;

        EObject this_JsonTag_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2165:28: (this_JsonTag_0= ruleJsonTag )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2167:5: this_JsonTag_0= ruleJsonTag
            {
             
                    newCompositeNode(grammarAccess.getTagAccess().getJsonTagParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleJsonTag_in_ruleTag5113);
            this_JsonTag_0=ruleJsonTag();

            state._fsp--;

             
                    current = this_JsonTag_0; 
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTag"


    // $ANTLR start "entryRuleJsonTag"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2183:1: entryRuleJsonTag returns [EObject current=null] : iv_ruleJsonTag= ruleJsonTag EOF ;
    public final EObject entryRuleJsonTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonTag = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2184:2: (iv_ruleJsonTag= ruleJsonTag EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2185:2: iv_ruleJsonTag= ruleJsonTag EOF
            {
             newCompositeNode(grammarAccess.getJsonTagRule()); 
            pushFollow(FOLLOW_ruleJsonTag_in_entryRuleJsonTag5147);
            iv_ruleJsonTag=ruleJsonTag();

            state._fsp--;

             current =iv_ruleJsonTag; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonTag5157); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonTag"


    // $ANTLR start "ruleJsonTag"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2192:1: ruleJsonTag returns [EObject current=null] : ( (lv_value_0_0= ruleQ_StringOrKey ) ) ;
    public final EObject ruleJsonTag() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2195:28: ( ( (lv_value_0_0= ruleQ_StringOrKey ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2196:1: ( (lv_value_0_0= ruleQ_StringOrKey ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2196:1: ( (lv_value_0_0= ruleQ_StringOrKey ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2197:1: (lv_value_0_0= ruleQ_StringOrKey )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2197:1: (lv_value_0_0= ruleQ_StringOrKey )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2198:3: lv_value_0_0= ruleQ_StringOrKey
            {
             
            	        newCompositeNode(grammarAccess.getJsonTagAccess().getValueQ_StringOrKeyParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleQ_StringOrKey_in_ruleJsonTag5202);
            lv_value_0_0=ruleQ_StringOrKey();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getJsonTagRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"Q_StringOrKey");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonTag"


    // $ANTLR start "entryRuleVersion"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2222:1: entryRuleVersion returns [EObject current=null] : iv_ruleVersion= ruleVersion EOF ;
    public final EObject entryRuleVersion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersion = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2223:2: (iv_ruleVersion= ruleVersion EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2224:2: iv_ruleVersion= ruleVersion EOF
            {
             newCompositeNode(grammarAccess.getVersionRule()); 
            pushFollow(FOLLOW_ruleVersion_in_entryRuleVersion5237);
            iv_ruleVersion=ruleVersion();

            state._fsp--;

             current =iv_ruleVersion; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVersion5247); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVersion"


    // $ANTLR start "ruleVersion"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2231:1: ruleVersion returns [EObject current=null] : this_JsonVersion_0= ruleJsonVersion ;
    public final EObject ruleVersion() throws RecognitionException {
        EObject current = null;

        EObject this_JsonVersion_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2234:28: (this_JsonVersion_0= ruleJsonVersion )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2236:5: this_JsonVersion_0= ruleJsonVersion
            {
             
                    newCompositeNode(grammarAccess.getVersionAccess().getJsonVersionParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleJsonVersion_in_ruleVersion5293);
            this_JsonVersion_0=ruleJsonVersion();

            state._fsp--;

             
                    current = this_JsonVersion_0; 
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersion"


    // $ANTLR start "entryRuleJsonVersion"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2252:1: entryRuleJsonVersion returns [EObject current=null] : iv_ruleJsonVersion= ruleJsonVersion EOF ;
    public final EObject entryRuleJsonVersion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonVersion = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2253:2: (iv_ruleJsonVersion= ruleJsonVersion EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2254:2: iv_ruleJsonVersion= ruleJsonVersion EOF
            {
             newCompositeNode(grammarAccess.getJsonVersionRule()); 
            pushFollow(FOLLOW_ruleJsonVersion_in_entryRuleJsonVersion5327);
            iv_ruleJsonVersion=ruleJsonVersion();

            state._fsp--;

             current =iv_ruleJsonVersion; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonVersion5337); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonVersion"


    // $ANTLR start "ruleJsonVersion"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2261:1: ruleJsonVersion returns [EObject current=null] : ( (lv_value_0_0= RULE_STRING ) ) ;
    public final EObject ruleJsonVersion() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2264:28: ( ( (lv_value_0_0= RULE_STRING ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2265:1: ( (lv_value_0_0= RULE_STRING ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2265:1: ( (lv_value_0_0= RULE_STRING ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2266:1: (lv_value_0_0= RULE_STRING )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2266:1: (lv_value_0_0= RULE_STRING )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2267:3: lv_value_0_0= RULE_STRING
            {
            lv_value_0_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleJsonVersion5378); 

            			newLeafNode(lv_value_0_0, grammarAccess.getJsonVersionAccess().getValueSTRINGTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getJsonVersionRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"STRING");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonVersion"


    // $ANTLR start "entryRuleOSArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2291:1: entryRuleOSArray returns [EObject current=null] : iv_ruleOSArray= ruleOSArray EOF ;
    public final EObject entryRuleOSArray() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOSArray = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2292:2: (iv_ruleOSArray= ruleOSArray EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2293:2: iv_ruleOSArray= ruleOSArray EOF
            {
             newCompositeNode(grammarAccess.getOSArrayRule()); 
            pushFollow(FOLLOW_ruleOSArray_in_entryRuleOSArray5418);
            iv_ruleOSArray=ruleOSArray();

            state._fsp--;

             current =iv_ruleOSArray; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOSArray5428); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOSArray"


    // $ANTLR start "ruleOSArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2300:1: ruleOSArray returns [EObject current=null] : ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleOSObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleOSObject ) ) )* )? otherlv_5= ']' ) ;
    public final EObject ruleOSArray() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_value_2_0 = null;

        EObject lv_value_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2303:28: ( ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleOSObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleOSObject ) ) )* )? otherlv_5= ']' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2304:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleOSObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleOSObject ) ) )* )? otherlv_5= ']' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2304:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleOSObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleOSObject ) ) )* )? otherlv_5= ']' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2304:2: () otherlv_1= '[' ( ( (lv_value_2_0= ruleOSObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleOSObject ) ) )* )? otherlv_5= ']'
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2304:2: ()
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2305:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getOSArrayAccess().getJsonArrayAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,26,FOLLOW_26_in_ruleOSArray5474); 

                	newLeafNode(otherlv_1, grammarAccess.getOSArrayAccess().getLeftSquareBracketKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2314:1: ( ( (lv_value_2_0= ruleOSObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleOSObject ) ) )* )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==10) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2314:2: ( (lv_value_2_0= ruleOSObject ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleOSObject ) ) )*
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2314:2: ( (lv_value_2_0= ruleOSObject ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2315:1: (lv_value_2_0= ruleOSObject )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2315:1: (lv_value_2_0= ruleOSObject )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2316:3: lv_value_2_0= ruleOSObject
                    {
                     
                    	        newCompositeNode(grammarAccess.getOSArrayAccess().getValueOSObjectParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleOSObject_in_ruleOSArray5496);
                    lv_value_2_0=ruleOSObject();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getOSArrayRule());
                    	        }
                           		add(
                           			current, 
                           			"value",
                            		lv_value_2_0, 
                            		"OSObject");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2332:2: (otherlv_3= ',' ( (lv_value_4_0= ruleOSObject ) ) )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==11) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2332:4: otherlv_3= ',' ( (lv_value_4_0= ruleOSObject ) )
                    	    {
                    	    otherlv_3=(Token)match(input,11,FOLLOW_11_in_ruleOSArray5509); 

                    	        	newLeafNode(otherlv_3, grammarAccess.getOSArrayAccess().getCommaKeyword_2_1_0());
                    	        
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2336:1: ( (lv_value_4_0= ruleOSObject ) )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2337:1: (lv_value_4_0= ruleOSObject )
                    	    {
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2337:1: (lv_value_4_0= ruleOSObject )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2338:3: lv_value_4_0= ruleOSObject
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getOSArrayAccess().getValueOSObjectParserRuleCall_2_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleOSObject_in_ruleOSArray5530);
                    	    lv_value_4_0=ruleOSObject();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getOSArrayRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"value",
                    	            		lv_value_4_0, 
                    	            		"OSObject");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop17;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,27,FOLLOW_27_in_ruleOSArray5546); 

                	newLeafNode(otherlv_5, grammarAccess.getOSArrayAccess().getRightSquareBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOSArray"


    // $ANTLR start "entryRuleOSObject"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2366:1: entryRuleOSObject returns [EObject current=null] : iv_ruleOSObject= ruleOSObject EOF ;
    public final EObject entryRuleOSObject() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOSObject = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2367:2: (iv_ruleOSObject= ruleOSObject EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2368:2: iv_ruleOSObject= ruleOSObject EOF
            {
             newCompositeNode(grammarAccess.getOSObjectRule()); 
            pushFollow(FOLLOW_ruleOSObject_in_entryRuleOSObject5582);
            iv_ruleOSObject=ruleOSObject();

            state._fsp--;

             current =iv_ruleOSObject; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOSObject5592); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOSObject"


    // $ANTLR start "ruleOSObject"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2375:1: ruleOSObject returns [EObject current=null] : this_JsonOS_0= ruleJsonOS ;
    public final EObject ruleOSObject() throws RecognitionException {
        EObject current = null;

        EObject this_JsonOS_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2378:28: (this_JsonOS_0= ruleJsonOS )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2380:5: this_JsonOS_0= ruleJsonOS
            {
             
                    newCompositeNode(grammarAccess.getOSObjectAccess().getJsonOSParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleJsonOS_in_ruleOSObject5638);
            this_JsonOS_0=ruleJsonOS();

            state._fsp--;

             
                    current = this_JsonOS_0; 
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOSObject"


    // $ANTLR start "entryRuleJsonOS"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2396:1: entryRuleJsonOS returns [EObject current=null] : iv_ruleJsonOS= ruleJsonOS EOF ;
    public final EObject entryRuleJsonOS() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonOS = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2397:2: (iv_ruleJsonOS= ruleJsonOS EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2398:2: iv_ruleJsonOS= ruleJsonOS EOF
            {
             newCompositeNode(grammarAccess.getJsonOSRule()); 
            pushFollow(FOLLOW_ruleJsonOS_in_entryRuleJsonOS5672);
            iv_ruleJsonOS=ruleJsonOS();

            state._fsp--;

             current =iv_ruleJsonOS; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonOS5682); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonOS"


    // $ANTLR start "ruleJsonOS"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2405:1: ruleJsonOS returns [EObject current=null] : (otherlv_0= '{' ( (lv_pairs_1_0= ruleOSPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleOSPair ) ) )* otherlv_4= '}' ) ;
    public final EObject ruleJsonOS() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_pairs_1_0 = null;

        EObject lv_pairs_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2408:28: ( (otherlv_0= '{' ( (lv_pairs_1_0= ruleOSPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleOSPair ) ) )* otherlv_4= '}' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2409:1: (otherlv_0= '{' ( (lv_pairs_1_0= ruleOSPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleOSPair ) ) )* otherlv_4= '}' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2409:1: (otherlv_0= '{' ( (lv_pairs_1_0= ruleOSPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleOSPair ) ) )* otherlv_4= '}' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2409:3: otherlv_0= '{' ( (lv_pairs_1_0= ruleOSPair ) ) (otherlv_2= ',' ( (lv_pairs_3_0= ruleOSPair ) ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,10,FOLLOW_10_in_ruleJsonOS5719); 

                	newLeafNode(otherlv_0, grammarAccess.getJsonOSAccess().getLeftCurlyBracketKeyword_0());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2413:1: ( (lv_pairs_1_0= ruleOSPair ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2414:1: (lv_pairs_1_0= ruleOSPair )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2414:1: (lv_pairs_1_0= ruleOSPair )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2415:3: lv_pairs_1_0= ruleOSPair
            {
             
            	        newCompositeNode(grammarAccess.getJsonOSAccess().getPairsOSPairParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleOSPair_in_ruleJsonOS5740);
            lv_pairs_1_0=ruleOSPair();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getJsonOSRule());
            	        }
                   		add(
                   			current, 
                   			"pairs",
                    		lv_pairs_1_0, 
                    		"OSPair");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2431:2: (otherlv_2= ',' ( (lv_pairs_3_0= ruleOSPair ) ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==11) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2431:4: otherlv_2= ',' ( (lv_pairs_3_0= ruleOSPair ) )
            	    {
            	    otherlv_2=(Token)match(input,11,FOLLOW_11_in_ruleJsonOS5753); 

            	        	newLeafNode(otherlv_2, grammarAccess.getJsonOSAccess().getCommaKeyword_2_0());
            	        
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2435:1: ( (lv_pairs_3_0= ruleOSPair ) )
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2436:1: (lv_pairs_3_0= ruleOSPair )
            	    {
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2436:1: (lv_pairs_3_0= ruleOSPair )
            	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2437:3: lv_pairs_3_0= ruleOSPair
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getJsonOSAccess().getPairsOSPairParserRuleCall_2_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleOSPair_in_ruleJsonOS5774);
            	    lv_pairs_3_0=ruleOSPair();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getJsonOSRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"pairs",
            	            		lv_pairs_3_0, 
            	            		"OSPair");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

            otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleJsonOS5788); 

                	newLeafNode(otherlv_4, grammarAccess.getJsonOSAccess().getRightCurlyBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonOS"


    // $ANTLR start "entryRuleOSPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2465:1: entryRuleOSPair returns [EObject current=null] : iv_ruleOSPair= ruleOSPair EOF ;
    public final EObject entryRuleOSPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOSPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2466:2: (iv_ruleOSPair= ruleOSPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2467:2: iv_ruleOSPair= ruleOSPair EOF
            {
             newCompositeNode(grammarAccess.getOSPairRule()); 
            pushFollow(FOLLOW_ruleOSPair_in_entryRuleOSPair5824);
            iv_ruleOSPair=ruleOSPair();

            state._fsp--;

             current =iv_ruleOSPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOSPair5834); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOSPair"


    // $ANTLR start "ruleOSPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2474:1: ruleOSPair returns [EObject current=null] : ( ( ( (lv_name_0_0= '\"operatingsystem\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) | ( ( (lv_name_3_0= '\"operatingsystemrelease\"' ) ) otherlv_4= ':' ( (lv_value_5_0= ruleStringArray ) ) ) | this_UnrecognizedOSPair_6= ruleUnrecognizedOSPair ) ;
    public final EObject ruleOSPair() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_name_3_0=null;
        Token otherlv_4=null;
        EObject lv_value_2_0 = null;

        EObject lv_value_5_0 = null;

        EObject this_UnrecognizedOSPair_6 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2477:28: ( ( ( ( (lv_name_0_0= '\"operatingsystem\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) | ( ( (lv_name_3_0= '\"operatingsystemrelease\"' ) ) otherlv_4= ':' ( (lv_value_5_0= ruleStringArray ) ) ) | this_UnrecognizedOSPair_6= ruleUnrecognizedOSPair ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2478:1: ( ( ( (lv_name_0_0= '\"operatingsystem\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) | ( ( (lv_name_3_0= '\"operatingsystemrelease\"' ) ) otherlv_4= ':' ( (lv_value_5_0= ruleStringArray ) ) ) | this_UnrecognizedOSPair_6= ruleUnrecognizedOSPair )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2478:1: ( ( ( (lv_name_0_0= '\"operatingsystem\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) ) | ( ( (lv_name_3_0= '\"operatingsystemrelease\"' ) ) otherlv_4= ':' ( (lv_value_5_0= ruleStringArray ) ) ) | this_UnrecognizedOSPair_6= ruleUnrecognizedOSPair )
            int alt20=3;
            switch ( input.LA(1) ) {
            case 29:
                {
                alt20=1;
                }
                break;
            case 30:
                {
                alt20=2;
                }
                break;
            case RULE_STRING:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 28:
            case 31:
                {
                alt20=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2478:2: ( ( (lv_name_0_0= '\"operatingsystem\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2478:2: ( ( (lv_name_0_0= '\"operatingsystem\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2478:3: ( (lv_name_0_0= '\"operatingsystem\"' ) ) otherlv_1= ':' ( (lv_value_2_0= ruleStringLiteral ) )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2478:3: ( (lv_name_0_0= '\"operatingsystem\"' ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2479:1: (lv_name_0_0= '\"operatingsystem\"' )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2479:1: (lv_name_0_0= '\"operatingsystem\"' )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2480:3: lv_name_0_0= '\"operatingsystem\"'
                    {
                    lv_name_0_0=(Token)match(input,29,FOLLOW_29_in_ruleOSPair5878); 

                            newLeafNode(lv_name_0_0, grammarAccess.getOSPairAccess().getNameOperatingsystemKeyword_0_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getOSPairRule());
                    	        }
                           		setWithLastConsumed(current, "name", lv_name_0_0, "\"operatingsystem\"");
                    	    

                    }


                    }

                    otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleOSPair5903); 

                        	newLeafNode(otherlv_1, grammarAccess.getOSPairAccess().getColonKeyword_0_1());
                        
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2497:1: ( (lv_value_2_0= ruleStringLiteral ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2498:1: (lv_value_2_0= ruleStringLiteral )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2498:1: (lv_value_2_0= ruleStringLiteral )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2499:3: lv_value_2_0= ruleStringLiteral
                    {
                     
                    	        newCompositeNode(grammarAccess.getOSPairAccess().getValueStringLiteralParserRuleCall_0_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleStringLiteral_in_ruleOSPair5924);
                    lv_value_2_0=ruleStringLiteral();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getOSPairRule());
                    	        }
                           		set(
                           			current, 
                           			"value",
                            		lv_value_2_0, 
                            		"StringLiteral");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2516:6: ( ( (lv_name_3_0= '\"operatingsystemrelease\"' ) ) otherlv_4= ':' ( (lv_value_5_0= ruleStringArray ) ) )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2516:6: ( ( (lv_name_3_0= '\"operatingsystemrelease\"' ) ) otherlv_4= ':' ( (lv_value_5_0= ruleStringArray ) ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2516:7: ( (lv_name_3_0= '\"operatingsystemrelease\"' ) ) otherlv_4= ':' ( (lv_value_5_0= ruleStringArray ) )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2516:7: ( (lv_name_3_0= '\"operatingsystemrelease\"' ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2517:1: (lv_name_3_0= '\"operatingsystemrelease\"' )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2517:1: (lv_name_3_0= '\"operatingsystemrelease\"' )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2518:3: lv_name_3_0= '\"operatingsystemrelease\"'
                    {
                    lv_name_3_0=(Token)match(input,30,FOLLOW_30_in_ruleOSPair5950); 

                            newLeafNode(lv_name_3_0, grammarAccess.getOSPairAccess().getNameOperatingsystemreleaseKeyword_1_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getOSPairRule());
                    	        }
                           		setWithLastConsumed(current, "name", lv_name_3_0, "\"operatingsystemrelease\"");
                    	    

                    }


                    }

                    otherlv_4=(Token)match(input,14,FOLLOW_14_in_ruleOSPair5975); 

                        	newLeafNode(otherlv_4, grammarAccess.getOSPairAccess().getColonKeyword_1_1());
                        
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2535:1: ( (lv_value_5_0= ruleStringArray ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2536:1: (lv_value_5_0= ruleStringArray )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2536:1: (lv_value_5_0= ruleStringArray )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2537:3: lv_value_5_0= ruleStringArray
                    {
                     
                    	        newCompositeNode(grammarAccess.getOSPairAccess().getValueStringArrayParserRuleCall_1_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleStringArray_in_ruleOSPair5996);
                    lv_value_5_0=ruleStringArray();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getOSPairRule());
                    	        }
                           		set(
                           			current, 
                           			"value",
                            		lv_value_5_0, 
                            		"StringArray");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2555:5: this_UnrecognizedOSPair_6= ruleUnrecognizedOSPair
                    {
                     
                            newCompositeNode(grammarAccess.getOSPairAccess().getUnrecognizedOSPairParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleUnrecognizedOSPair_in_ruleOSPair6025);
                    this_UnrecognizedOSPair_6=ruleUnrecognizedOSPair();

                    state._fsp--;

                     
                            current = this_UnrecognizedOSPair_6; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOSPair"


    // $ANTLR start "entryRuleUnrecognizedOSPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2571:1: entryRuleUnrecognizedOSPair returns [EObject current=null] : iv_ruleUnrecognizedOSPair= ruleUnrecognizedOSPair EOF ;
    public final EObject entryRuleUnrecognizedOSPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnrecognizedOSPair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2572:2: (iv_ruleUnrecognizedOSPair= ruleUnrecognizedOSPair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2573:2: iv_ruleUnrecognizedOSPair= ruleUnrecognizedOSPair EOF
            {
             newCompositeNode(grammarAccess.getUnrecognizedOSPairRule()); 
            pushFollow(FOLLOW_ruleUnrecognizedOSPair_in_entryRuleUnrecognizedOSPair6060);
            iv_ruleUnrecognizedOSPair=ruleUnrecognizedOSPair();

            state._fsp--;

             current =iv_ruleUnrecognizedOSPair; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleUnrecognizedOSPair6070); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnrecognizedOSPair"


    // $ANTLR start "ruleUnrecognizedOSPair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2580:1: ruleUnrecognizedOSPair returns [EObject current=null] : ( ( (lv_name_0_0= ruleQ_OsUnknownKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleUnrecognizedOSPair() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2583:28: ( ( ( (lv_name_0_0= ruleQ_OsUnknownKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2584:1: ( ( (lv_name_0_0= ruleQ_OsUnknownKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2584:1: ( ( (lv_name_0_0= ruleQ_OsUnknownKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2584:2: ( (lv_name_0_0= ruleQ_OsUnknownKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2584:2: ( (lv_name_0_0= ruleQ_OsUnknownKey ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2585:1: (lv_name_0_0= ruleQ_OsUnknownKey )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2585:1: (lv_name_0_0= ruleQ_OsUnknownKey )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2586:3: lv_name_0_0= ruleQ_OsUnknownKey
            {
             
            	        newCompositeNode(grammarAccess.getUnrecognizedOSPairAccess().getNameQ_OsUnknownKeyParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleQ_OsUnknownKey_in_ruleUnrecognizedOSPair6116);
            lv_name_0_0=ruleQ_OsUnknownKey();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getUnrecognizedOSPairRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"Q_OsUnknownKey");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_ruleUnrecognizedOSPair6128); 

                	newLeafNode(otherlv_1, grammarAccess.getUnrecognizedOSPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2606:1: ( (lv_value_2_0= ruleValue ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2607:1: (lv_value_2_0= ruleValue )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2607:1: (lv_value_2_0= ruleValue )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2608:3: lv_value_2_0= ruleValue
            {
             
            	        newCompositeNode(grammarAccess.getUnrecognizedOSPairAccess().getValueValueParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleValue_in_ruleUnrecognizedOSPair6149);
            lv_value_2_0=ruleValue();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getUnrecognizedOSPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnrecognizedOSPair"


    // $ANTLR start "entryRulePair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2632:1: entryRulePair returns [EObject current=null] : iv_rulePair= rulePair EOF ;
    public final EObject entryRulePair() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePair = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2633:2: (iv_rulePair= rulePair EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2634:2: iv_rulePair= rulePair EOF
            {
             newCompositeNode(grammarAccess.getPairRule()); 
            pushFollow(FOLLOW_rulePair_in_entryRulePair6185);
            iv_rulePair=rulePair();

            state._fsp--;

             current =iv_rulePair; 
            match(input,EOF,FOLLOW_EOF_in_entryRulePair6195); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePair"


    // $ANTLR start "rulePair"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2641:1: rulePair returns [EObject current=null] : ( ( (lv_name_0_0= ruleQ_StringOrKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject rulePair() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        EObject lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2644:28: ( ( ( (lv_name_0_0= ruleQ_StringOrKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2645:1: ( ( (lv_name_0_0= ruleQ_StringOrKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2645:1: ( ( (lv_name_0_0= ruleQ_StringOrKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2645:2: ( (lv_name_0_0= ruleQ_StringOrKey ) ) otherlv_1= ':' ( (lv_value_2_0= ruleValue ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2645:2: ( (lv_name_0_0= ruleQ_StringOrKey ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2646:1: (lv_name_0_0= ruleQ_StringOrKey )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2646:1: (lv_name_0_0= ruleQ_StringOrKey )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2647:3: lv_name_0_0= ruleQ_StringOrKey
            {
             
            	        newCompositeNode(grammarAccess.getPairAccess().getNameQ_StringOrKeyParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleQ_StringOrKey_in_rulePair6241);
            lv_name_0_0=ruleQ_StringOrKey();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getPairRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"Q_StringOrKey");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_1=(Token)match(input,14,FOLLOW_14_in_rulePair6253); 

                	newLeafNode(otherlv_1, grammarAccess.getPairAccess().getColonKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2667:1: ( (lv_value_2_0= ruleValue ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2668:1: (lv_value_2_0= ruleValue )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2668:1: (lv_value_2_0= ruleValue )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2669:3: lv_value_2_0= ruleValue
            {
             
            	        newCompositeNode(grammarAccess.getPairAccess().getValueValueParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleValue_in_rulePair6274);
            lv_value_2_0=ruleValue();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getPairRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePair"


    // $ANTLR start "entryRuleValue"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2693:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2694:2: (iv_ruleValue= ruleValue EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2695:2: iv_ruleValue= ruleValue EOF
            {
             newCompositeNode(grammarAccess.getValueRule()); 
            pushFollow(FOLLOW_ruleValue_in_entryRuleValue6310);
            iv_ruleValue=ruleValue();

            state._fsp--;

             current =iv_ruleValue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleValue6320); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValue"


    // $ANTLR start "ruleValue"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2702:1: ruleValue returns [EObject current=null] : (this_BooleanLiteral_0= ruleBooleanLiteral | this_NullValue_1= ruleNullValue | this_StringLiteral_2= ruleStringLiteral | this_DoubleLiteral_3= ruleDoubleLiteral | this_LongLiteral_4= ruleLongLiteral | this_JsonObject_5= ruleJsonObject | this_JsonArray_6= ruleJsonArray ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        EObject this_BooleanLiteral_0 = null;

        EObject this_NullValue_1 = null;

        EObject this_StringLiteral_2 = null;

        EObject this_DoubleLiteral_3 = null;

        EObject this_LongLiteral_4 = null;

        EObject this_JsonObject_5 = null;

        EObject this_JsonArray_6 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2705:28: ( (this_BooleanLiteral_0= ruleBooleanLiteral | this_NullValue_1= ruleNullValue | this_StringLiteral_2= ruleStringLiteral | this_DoubleLiteral_3= ruleDoubleLiteral | this_LongLiteral_4= ruleLongLiteral | this_JsonObject_5= ruleJsonObject | this_JsonArray_6= ruleJsonArray ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2706:1: (this_BooleanLiteral_0= ruleBooleanLiteral | this_NullValue_1= ruleNullValue | this_StringLiteral_2= ruleStringLiteral | this_DoubleLiteral_3= ruleDoubleLiteral | this_LongLiteral_4= ruleLongLiteral | this_JsonObject_5= ruleJsonObject | this_JsonArray_6= ruleJsonArray )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2706:1: (this_BooleanLiteral_0= ruleBooleanLiteral | this_NullValue_1= ruleNullValue | this_StringLiteral_2= ruleStringLiteral | this_DoubleLiteral_3= ruleDoubleLiteral | this_LongLiteral_4= ruleLongLiteral | this_JsonObject_5= ruleJsonObject | this_JsonArray_6= ruleJsonArray )
            int alt21=7;
            switch ( input.LA(1) ) {
            case 32:
            case 33:
                {
                alt21=1;
                }
                break;
            case 34:
                {
                alt21=2;
                }
                break;
            case RULE_STRING:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 28:
            case 29:
            case 30:
            case 31:
                {
                alt21=3;
                }
                break;
            case RULE_DOUBLE:
                {
                alt21=4;
                }
                break;
            case RULE_LONG:
                {
                alt21=5;
                }
                break;
            case 10:
                {
                alt21=6;
                }
                break;
            case 26:
                {
                alt21=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }

            switch (alt21) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2707:5: this_BooleanLiteral_0= ruleBooleanLiteral
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getBooleanLiteralParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleBooleanLiteral_in_ruleValue6367);
                    this_BooleanLiteral_0=ruleBooleanLiteral();

                    state._fsp--;

                     
                            current = this_BooleanLiteral_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2717:5: this_NullValue_1= ruleNullValue
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getNullValueParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleNullValue_in_ruleValue6394);
                    this_NullValue_1=ruleNullValue();

                    state._fsp--;

                     
                            current = this_NullValue_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2727:5: this_StringLiteral_2= ruleStringLiteral
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getStringLiteralParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleStringLiteral_in_ruleValue6421);
                    this_StringLiteral_2=ruleStringLiteral();

                    state._fsp--;

                     
                            current = this_StringLiteral_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2737:5: this_DoubleLiteral_3= ruleDoubleLiteral
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getDoubleLiteralParserRuleCall_3()); 
                        
                    pushFollow(FOLLOW_ruleDoubleLiteral_in_ruleValue6448);
                    this_DoubleLiteral_3=ruleDoubleLiteral();

                    state._fsp--;

                     
                            current = this_DoubleLiteral_3; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2747:5: this_LongLiteral_4= ruleLongLiteral
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getLongLiteralParserRuleCall_4()); 
                        
                    pushFollow(FOLLOW_ruleLongLiteral_in_ruleValue6475);
                    this_LongLiteral_4=ruleLongLiteral();

                    state._fsp--;

                     
                            current = this_LongLiteral_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2757:5: this_JsonObject_5= ruleJsonObject
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getJsonObjectParserRuleCall_5()); 
                        
                    pushFollow(FOLLOW_ruleJsonObject_in_ruleValue6502);
                    this_JsonObject_5=ruleJsonObject();

                    state._fsp--;

                     
                            current = this_JsonObject_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2767:5: this_JsonArray_6= ruleJsonArray
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getJsonArrayParserRuleCall_6()); 
                        
                    pushFollow(FOLLOW_ruleJsonArray_in_ruleValue6529);
                    this_JsonArray_6=ruleJsonArray();

                    state._fsp--;

                     
                            current = this_JsonArray_6; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRuleStringLiteral"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2783:1: entryRuleStringLiteral returns [EObject current=null] : iv_ruleStringLiteral= ruleStringLiteral EOF ;
    public final EObject entryRuleStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStringLiteral = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2784:2: (iv_ruleStringLiteral= ruleStringLiteral EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2785:2: iv_ruleStringLiteral= ruleStringLiteral EOF
            {
             newCompositeNode(grammarAccess.getStringLiteralRule()); 
            pushFollow(FOLLOW_ruleStringLiteral_in_entryRuleStringLiteral6564);
            iv_ruleStringLiteral=ruleStringLiteral();

            state._fsp--;

             current =iv_ruleStringLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleStringLiteral6574); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStringLiteral"


    // $ANTLR start "ruleStringLiteral"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2792:1: ruleStringLiteral returns [EObject current=null] : ( (lv_value_0_0= ruleQ_StringOrKey ) ) ;
    public final EObject ruleStringLiteral() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2795:28: ( ( (lv_value_0_0= ruleQ_StringOrKey ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2796:1: ( (lv_value_0_0= ruleQ_StringOrKey ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2796:1: ( (lv_value_0_0= ruleQ_StringOrKey ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2797:1: (lv_value_0_0= ruleQ_StringOrKey )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2797:1: (lv_value_0_0= ruleQ_StringOrKey )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2798:3: lv_value_0_0= ruleQ_StringOrKey
            {
             
            	        newCompositeNode(grammarAccess.getStringLiteralAccess().getValueQ_StringOrKeyParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleQ_StringOrKey_in_ruleStringLiteral6619);
            lv_value_0_0=ruleQ_StringOrKey();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getStringLiteralRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"Q_StringOrKey");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStringLiteral"


    // $ANTLR start "entryRuleQ_StringOrKey"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2822:1: entryRuleQ_StringOrKey returns [String current=null] : iv_ruleQ_StringOrKey= ruleQ_StringOrKey EOF ;
    public final String entryRuleQ_StringOrKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQ_StringOrKey = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2823:2: (iv_ruleQ_StringOrKey= ruleQ_StringOrKey EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2824:2: iv_ruleQ_StringOrKey= ruleQ_StringOrKey EOF
            {
             newCompositeNode(grammarAccess.getQ_StringOrKeyRule()); 
            pushFollow(FOLLOW_ruleQ_StringOrKey_in_entryRuleQ_StringOrKey6655);
            iv_ruleQ_StringOrKey=ruleQ_StringOrKey();

            state._fsp--;

             current =iv_ruleQ_StringOrKey.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleQ_StringOrKey6666); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQ_StringOrKey"


    // $ANTLR start "ruleQ_StringOrKey"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2831:1: ruleQ_StringOrKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"name\"' | kw= '\"operatingsystem\"' | kw= '\"operatingsystem_support\"' | kw= '\"operatingsystemrelease\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | kw= '\"version_requirement\"' | this_STRING_16= RULE_STRING ) ;
    public final AntlrDatatypeRuleToken ruleQ_StringOrKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_STRING_16=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2834:28: ( (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"name\"' | kw= '\"operatingsystem\"' | kw= '\"operatingsystem_support\"' | kw= '\"operatingsystemrelease\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | kw= '\"version_requirement\"' | this_STRING_16= RULE_STRING ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2835:1: (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"name\"' | kw= '\"operatingsystem\"' | kw= '\"operatingsystem_support\"' | kw= '\"operatingsystemrelease\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | kw= '\"version_requirement\"' | this_STRING_16= RULE_STRING )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2835:1: (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"name\"' | kw= '\"operatingsystem\"' | kw= '\"operatingsystem_support\"' | kw= '\"operatingsystemrelease\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | kw= '\"version_requirement\"' | this_STRING_16= RULE_STRING )
            int alt22=17;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt22=1;
                }
                break;
            case 15:
                {
                alt22=2;
                }
                break;
            case 16:
                {
                alt22=3;
                }
                break;
            case 17:
                {
                alt22=4;
                }
                break;
            case 18:
                {
                alt22=5;
                }
                break;
            case 29:
                {
                alt22=6;
                }
                break;
            case 19:
                {
                alt22=7;
                }
                break;
            case 30:
                {
                alt22=8;
                }
                break;
            case 31:
                {
                alt22=9;
                }
                break;
            case 20:
                {
                alt22=10;
                }
                break;
            case 21:
                {
                alt22=11;
                }
                break;
            case 22:
                {
                alt22=12;
                }
                break;
            case 23:
                {
                alt22=13;
                }
                break;
            case 24:
                {
                alt22=14;
                }
                break;
            case 25:
                {
                alt22=15;
                }
                break;
            case 28:
                {
                alt22=16;
                }
                break;
            case RULE_STRING:
                {
                alt22=17;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }

            switch (alt22) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2836:2: kw= '\"author\"'
                    {
                    kw=(Token)match(input,13,FOLLOW_13_in_ruleQ_StringOrKey6704); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getAuthorKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2843:2: kw= '\"dependencies\"'
                    {
                    kw=(Token)match(input,15,FOLLOW_15_in_ruleQ_StringOrKey6723); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getDependenciesKeyword_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2850:2: kw= '\"issues_url\"'
                    {
                    kw=(Token)match(input,16,FOLLOW_16_in_ruleQ_StringOrKey6742); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getIssues_urlKeyword_2()); 
                        

                    }
                    break;
                case 4 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2857:2: kw= '\"license\"'
                    {
                    kw=(Token)match(input,17,FOLLOW_17_in_ruleQ_StringOrKey6761); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getLicenseKeyword_3()); 
                        

                    }
                    break;
                case 5 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2864:2: kw= '\"name\"'
                    {
                    kw=(Token)match(input,18,FOLLOW_18_in_ruleQ_StringOrKey6780); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getNameKeyword_4()); 
                        

                    }
                    break;
                case 6 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2871:2: kw= '\"operatingsystem\"'
                    {
                    kw=(Token)match(input,29,FOLLOW_29_in_ruleQ_StringOrKey6799); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getOperatingsystemKeyword_5()); 
                        

                    }
                    break;
                case 7 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2878:2: kw= '\"operatingsystem_support\"'
                    {
                    kw=(Token)match(input,19,FOLLOW_19_in_ruleQ_StringOrKey6818); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getOperatingsystem_supportKeyword_6()); 
                        

                    }
                    break;
                case 8 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2885:2: kw= '\"operatingsystemrelease\"'
                    {
                    kw=(Token)match(input,30,FOLLOW_30_in_ruleQ_StringOrKey6837); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getOperatingsystemreleaseKeyword_7()); 
                        

                    }
                    break;
                case 9 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2892:2: kw= '\"parameters\"'
                    {
                    kw=(Token)match(input,31,FOLLOW_31_in_ruleQ_StringOrKey6856); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getParametersKeyword_8()); 
                        

                    }
                    break;
                case 10 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2899:2: kw= '\"project_page\"'
                    {
                    kw=(Token)match(input,20,FOLLOW_20_in_ruleQ_StringOrKey6875); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getProject_pageKeyword_9()); 
                        

                    }
                    break;
                case 11 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2906:2: kw= '\"requirements\"'
                    {
                    kw=(Token)match(input,21,FOLLOW_21_in_ruleQ_StringOrKey6894); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getRequirementsKeyword_10()); 
                        

                    }
                    break;
                case 12 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2913:2: kw= '\"source\"'
                    {
                    kw=(Token)match(input,22,FOLLOW_22_in_ruleQ_StringOrKey6913); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getSourceKeyword_11()); 
                        

                    }
                    break;
                case 13 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2920:2: kw= '\"summary\"'
                    {
                    kw=(Token)match(input,23,FOLLOW_23_in_ruleQ_StringOrKey6932); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getSummaryKeyword_12()); 
                        

                    }
                    break;
                case 14 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2927:2: kw= '\"tags\"'
                    {
                    kw=(Token)match(input,24,FOLLOW_24_in_ruleQ_StringOrKey6951); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getTagsKeyword_13()); 
                        

                    }
                    break;
                case 15 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2934:2: kw= '\"version\"'
                    {
                    kw=(Token)match(input,25,FOLLOW_25_in_ruleQ_StringOrKey6970); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getVersionKeyword_14()); 
                        

                    }
                    break;
                case 16 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2941:2: kw= '\"version_requirement\"'
                    {
                    kw=(Token)match(input,28,FOLLOW_28_in_ruleQ_StringOrKey6989); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_StringOrKeyAccess().getVersion_requirementKeyword_15()); 
                        

                    }
                    break;
                case 17 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2947:10: this_STRING_16= RULE_STRING
                    {
                    this_STRING_16=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleQ_StringOrKey7010); 

                    		current.merge(this_STRING_16);
                        
                     
                        newLeafNode(this_STRING_16, grammarAccess.getQ_StringOrKeyAccess().getSTRINGTerminalRuleCall_16()); 
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQ_StringOrKey"


    // $ANTLR start "entryRuleQ_ReqUnknownKey"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2962:1: entryRuleQ_ReqUnknownKey returns [String current=null] : iv_ruleQ_ReqUnknownKey= ruleQ_ReqUnknownKey EOF ;
    public final String entryRuleQ_ReqUnknownKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQ_ReqUnknownKey = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2963:2: (iv_ruleQ_ReqUnknownKey= ruleQ_ReqUnknownKey EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2964:2: iv_ruleQ_ReqUnknownKey= ruleQ_ReqUnknownKey EOF
            {
             newCompositeNode(grammarAccess.getQ_ReqUnknownKeyRule()); 
            pushFollow(FOLLOW_ruleQ_ReqUnknownKey_in_entryRuleQ_ReqUnknownKey7056);
            iv_ruleQ_ReqUnknownKey=ruleQ_ReqUnknownKey();

            state._fsp--;

             current =iv_ruleQ_ReqUnknownKey.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleQ_ReqUnknownKey7067); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQ_ReqUnknownKey"


    // $ANTLR start "ruleQ_ReqUnknownKey"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2971:1: ruleQ_ReqUnknownKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"operatingsystem\"' | kw= '\"operatingsystem_support\"' | kw= '\"operatingsystemrelease\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | this_STRING_14= RULE_STRING ) ;
    public final AntlrDatatypeRuleToken ruleQ_ReqUnknownKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_STRING_14=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2974:28: ( (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"operatingsystem\"' | kw= '\"operatingsystem_support\"' | kw= '\"operatingsystemrelease\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | this_STRING_14= RULE_STRING ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2975:1: (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"operatingsystem\"' | kw= '\"operatingsystem_support\"' | kw= '\"operatingsystemrelease\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | this_STRING_14= RULE_STRING )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2975:1: (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"operatingsystem\"' | kw= '\"operatingsystem_support\"' | kw= '\"operatingsystemrelease\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | this_STRING_14= RULE_STRING )
            int alt23=15;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt23=1;
                }
                break;
            case 15:
                {
                alt23=2;
                }
                break;
            case 16:
                {
                alt23=3;
                }
                break;
            case 17:
                {
                alt23=4;
                }
                break;
            case 29:
                {
                alt23=5;
                }
                break;
            case 19:
                {
                alt23=6;
                }
                break;
            case 30:
                {
                alt23=7;
                }
                break;
            case 31:
                {
                alt23=8;
                }
                break;
            case 20:
                {
                alt23=9;
                }
                break;
            case 21:
                {
                alt23=10;
                }
                break;
            case 22:
                {
                alt23=11;
                }
                break;
            case 23:
                {
                alt23=12;
                }
                break;
            case 24:
                {
                alt23=13;
                }
                break;
            case 25:
                {
                alt23=14;
                }
                break;
            case RULE_STRING:
                {
                alt23=15;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2976:2: kw= '\"author\"'
                    {
                    kw=(Token)match(input,13,FOLLOW_13_in_ruleQ_ReqUnknownKey7105); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getAuthorKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2983:2: kw= '\"dependencies\"'
                    {
                    kw=(Token)match(input,15,FOLLOW_15_in_ruleQ_ReqUnknownKey7124); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getDependenciesKeyword_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2990:2: kw= '\"issues_url\"'
                    {
                    kw=(Token)match(input,16,FOLLOW_16_in_ruleQ_ReqUnknownKey7143); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getIssues_urlKeyword_2()); 
                        

                    }
                    break;
                case 4 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:2997:2: kw= '\"license\"'
                    {
                    kw=(Token)match(input,17,FOLLOW_17_in_ruleQ_ReqUnknownKey7162); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getLicenseKeyword_3()); 
                        

                    }
                    break;
                case 5 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3004:2: kw= '\"operatingsystem\"'
                    {
                    kw=(Token)match(input,29,FOLLOW_29_in_ruleQ_ReqUnknownKey7181); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getOperatingsystemKeyword_4()); 
                        

                    }
                    break;
                case 6 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3011:2: kw= '\"operatingsystem_support\"'
                    {
                    kw=(Token)match(input,19,FOLLOW_19_in_ruleQ_ReqUnknownKey7200); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getOperatingsystem_supportKeyword_5()); 
                        

                    }
                    break;
                case 7 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3018:2: kw= '\"operatingsystemrelease\"'
                    {
                    kw=(Token)match(input,30,FOLLOW_30_in_ruleQ_ReqUnknownKey7219); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getOperatingsystemreleaseKeyword_6()); 
                        

                    }
                    break;
                case 8 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3025:2: kw= '\"parameters\"'
                    {
                    kw=(Token)match(input,31,FOLLOW_31_in_ruleQ_ReqUnknownKey7238); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getParametersKeyword_7()); 
                        

                    }
                    break;
                case 9 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3032:2: kw= '\"project_page\"'
                    {
                    kw=(Token)match(input,20,FOLLOW_20_in_ruleQ_ReqUnknownKey7257); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getProject_pageKeyword_8()); 
                        

                    }
                    break;
                case 10 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3039:2: kw= '\"requirements\"'
                    {
                    kw=(Token)match(input,21,FOLLOW_21_in_ruleQ_ReqUnknownKey7276); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getRequirementsKeyword_9()); 
                        

                    }
                    break;
                case 11 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3046:2: kw= '\"source\"'
                    {
                    kw=(Token)match(input,22,FOLLOW_22_in_ruleQ_ReqUnknownKey7295); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getSourceKeyword_10()); 
                        

                    }
                    break;
                case 12 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3053:2: kw= '\"summary\"'
                    {
                    kw=(Token)match(input,23,FOLLOW_23_in_ruleQ_ReqUnknownKey7314); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getSummaryKeyword_11()); 
                        

                    }
                    break;
                case 13 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3060:2: kw= '\"tags\"'
                    {
                    kw=(Token)match(input,24,FOLLOW_24_in_ruleQ_ReqUnknownKey7333); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getTagsKeyword_12()); 
                        

                    }
                    break;
                case 14 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3067:2: kw= '\"version\"'
                    {
                    kw=(Token)match(input,25,FOLLOW_25_in_ruleQ_ReqUnknownKey7352); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_ReqUnknownKeyAccess().getVersionKeyword_13()); 
                        

                    }
                    break;
                case 15 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3073:10: this_STRING_14= RULE_STRING
                    {
                    this_STRING_14=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleQ_ReqUnknownKey7373); 

                    		current.merge(this_STRING_14);
                        
                     
                        newLeafNode(this_STRING_14, grammarAccess.getQ_ReqUnknownKeyAccess().getSTRINGTerminalRuleCall_14()); 
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQ_ReqUnknownKey"


    // $ANTLR start "entryRuleQ_OsUnknownKey"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3088:1: entryRuleQ_OsUnknownKey returns [String current=null] : iv_ruleQ_OsUnknownKey= ruleQ_OsUnknownKey EOF ;
    public final String entryRuleQ_OsUnknownKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQ_OsUnknownKey = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3089:2: (iv_ruleQ_OsUnknownKey= ruleQ_OsUnknownKey EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3090:2: iv_ruleQ_OsUnknownKey= ruleQ_OsUnknownKey EOF
            {
             newCompositeNode(grammarAccess.getQ_OsUnknownKeyRule()); 
            pushFollow(FOLLOW_ruleQ_OsUnknownKey_in_entryRuleQ_OsUnknownKey7419);
            iv_ruleQ_OsUnknownKey=ruleQ_OsUnknownKey();

            state._fsp--;

             current =iv_ruleQ_OsUnknownKey.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleQ_OsUnknownKey7430); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQ_OsUnknownKey"


    // $ANTLR start "ruleQ_OsUnknownKey"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3097:1: ruleQ_OsUnknownKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"name\"' | kw= '\"operatingsystem_support\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | kw= '\"version_requirement\"' | this_STRING_14= RULE_STRING ) ;
    public final AntlrDatatypeRuleToken ruleQ_OsUnknownKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_STRING_14=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3100:28: ( (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"name\"' | kw= '\"operatingsystem_support\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | kw= '\"version_requirement\"' | this_STRING_14= RULE_STRING ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3101:1: (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"name\"' | kw= '\"operatingsystem_support\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | kw= '\"version_requirement\"' | this_STRING_14= RULE_STRING )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3101:1: (kw= '\"author\"' | kw= '\"dependencies\"' | kw= '\"issues_url\"' | kw= '\"license\"' | kw= '\"name\"' | kw= '\"operatingsystem_support\"' | kw= '\"parameters\"' | kw= '\"project_page\"' | kw= '\"requirements\"' | kw= '\"source\"' | kw= '\"summary\"' | kw= '\"tags\"' | kw= '\"version\"' | kw= '\"version_requirement\"' | this_STRING_14= RULE_STRING )
            int alt24=15;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt24=1;
                }
                break;
            case 15:
                {
                alt24=2;
                }
                break;
            case 16:
                {
                alt24=3;
                }
                break;
            case 17:
                {
                alt24=4;
                }
                break;
            case 18:
                {
                alt24=5;
                }
                break;
            case 19:
                {
                alt24=6;
                }
                break;
            case 31:
                {
                alt24=7;
                }
                break;
            case 20:
                {
                alt24=8;
                }
                break;
            case 21:
                {
                alt24=9;
                }
                break;
            case 22:
                {
                alt24=10;
                }
                break;
            case 23:
                {
                alt24=11;
                }
                break;
            case 24:
                {
                alt24=12;
                }
                break;
            case 25:
                {
                alt24=13;
                }
                break;
            case 28:
                {
                alt24=14;
                }
                break;
            case RULE_STRING:
                {
                alt24=15;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3102:2: kw= '\"author\"'
                    {
                    kw=(Token)match(input,13,FOLLOW_13_in_ruleQ_OsUnknownKey7468); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getAuthorKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3109:2: kw= '\"dependencies\"'
                    {
                    kw=(Token)match(input,15,FOLLOW_15_in_ruleQ_OsUnknownKey7487); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getDependenciesKeyword_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3116:2: kw= '\"issues_url\"'
                    {
                    kw=(Token)match(input,16,FOLLOW_16_in_ruleQ_OsUnknownKey7506); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getIssues_urlKeyword_2()); 
                        

                    }
                    break;
                case 4 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3123:2: kw= '\"license\"'
                    {
                    kw=(Token)match(input,17,FOLLOW_17_in_ruleQ_OsUnknownKey7525); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getLicenseKeyword_3()); 
                        

                    }
                    break;
                case 5 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3130:2: kw= '\"name\"'
                    {
                    kw=(Token)match(input,18,FOLLOW_18_in_ruleQ_OsUnknownKey7544); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getNameKeyword_4()); 
                        

                    }
                    break;
                case 6 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3137:2: kw= '\"operatingsystem_support\"'
                    {
                    kw=(Token)match(input,19,FOLLOW_19_in_ruleQ_OsUnknownKey7563); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getOperatingsystem_supportKeyword_5()); 
                        

                    }
                    break;
                case 7 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3144:2: kw= '\"parameters\"'
                    {
                    kw=(Token)match(input,31,FOLLOW_31_in_ruleQ_OsUnknownKey7582); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getParametersKeyword_6()); 
                        

                    }
                    break;
                case 8 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3151:2: kw= '\"project_page\"'
                    {
                    kw=(Token)match(input,20,FOLLOW_20_in_ruleQ_OsUnknownKey7601); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getProject_pageKeyword_7()); 
                        

                    }
                    break;
                case 9 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3158:2: kw= '\"requirements\"'
                    {
                    kw=(Token)match(input,21,FOLLOW_21_in_ruleQ_OsUnknownKey7620); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getRequirementsKeyword_8()); 
                        

                    }
                    break;
                case 10 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3165:2: kw= '\"source\"'
                    {
                    kw=(Token)match(input,22,FOLLOW_22_in_ruleQ_OsUnknownKey7639); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getSourceKeyword_9()); 
                        

                    }
                    break;
                case 11 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3172:2: kw= '\"summary\"'
                    {
                    kw=(Token)match(input,23,FOLLOW_23_in_ruleQ_OsUnknownKey7658); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getSummaryKeyword_10()); 
                        

                    }
                    break;
                case 12 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3179:2: kw= '\"tags\"'
                    {
                    kw=(Token)match(input,24,FOLLOW_24_in_ruleQ_OsUnknownKey7677); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getTagsKeyword_11()); 
                        

                    }
                    break;
                case 13 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3186:2: kw= '\"version\"'
                    {
                    kw=(Token)match(input,25,FOLLOW_25_in_ruleQ_OsUnknownKey7696); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getVersionKeyword_12()); 
                        

                    }
                    break;
                case 14 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3193:2: kw= '\"version_requirement\"'
                    {
                    kw=(Token)match(input,28,FOLLOW_28_in_ruleQ_OsUnknownKey7715); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getQ_OsUnknownKeyAccess().getVersion_requirementKeyword_13()); 
                        

                    }
                    break;
                case 15 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3199:10: this_STRING_14= RULE_STRING
                    {
                    this_STRING_14=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleQ_OsUnknownKey7736); 

                    		current.merge(this_STRING_14);
                        
                     
                        newLeafNode(this_STRING_14, grammarAccess.getQ_OsUnknownKeyAccess().getSTRINGTerminalRuleCall_14()); 
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQ_OsUnknownKey"


    // $ANTLR start "entryRuleNullValue"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3214:1: entryRuleNullValue returns [EObject current=null] : iv_ruleNullValue= ruleNullValue EOF ;
    public final EObject entryRuleNullValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNullValue = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3215:2: (iv_ruleNullValue= ruleNullValue EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3216:2: iv_ruleNullValue= ruleNullValue EOF
            {
             newCompositeNode(grammarAccess.getNullValueRule()); 
            pushFollow(FOLLOW_ruleNullValue_in_entryRuleNullValue7781);
            iv_ruleNullValue=ruleNullValue();

            state._fsp--;

             current =iv_ruleNullValue; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNullValue7791); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNullValue"


    // $ANTLR start "ruleNullValue"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3223:1: ruleNullValue returns [EObject current=null] : ( (lv_value_0_0= ruleNULL ) ) ;
    public final EObject ruleNullValue() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3226:28: ( ( (lv_value_0_0= ruleNULL ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3227:1: ( (lv_value_0_0= ruleNULL ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3227:1: ( (lv_value_0_0= ruleNULL ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3228:1: (lv_value_0_0= ruleNULL )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3228:1: (lv_value_0_0= ruleNULL )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3229:3: lv_value_0_0= ruleNULL
            {
             
            	        newCompositeNode(grammarAccess.getNullValueAccess().getValueNULLParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleNULL_in_ruleNullValue7836);
            lv_value_0_0=ruleNULL();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getNullValueRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"NULL");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNullValue"


    // $ANTLR start "entryRuleDoubleLiteral"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3253:1: entryRuleDoubleLiteral returns [EObject current=null] : iv_ruleDoubleLiteral= ruleDoubleLiteral EOF ;
    public final EObject entryRuleDoubleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDoubleLiteral = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3254:2: (iv_ruleDoubleLiteral= ruleDoubleLiteral EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3255:2: iv_ruleDoubleLiteral= ruleDoubleLiteral EOF
            {
             newCompositeNode(grammarAccess.getDoubleLiteralRule()); 
            pushFollow(FOLLOW_ruleDoubleLiteral_in_entryRuleDoubleLiteral7871);
            iv_ruleDoubleLiteral=ruleDoubleLiteral();

            state._fsp--;

             current =iv_ruleDoubleLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDoubleLiteral7881); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDoubleLiteral"


    // $ANTLR start "ruleDoubleLiteral"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3262:1: ruleDoubleLiteral returns [EObject current=null] : ( (lv_value_0_0= RULE_DOUBLE ) ) ;
    public final EObject ruleDoubleLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3265:28: ( ( (lv_value_0_0= RULE_DOUBLE ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3266:1: ( (lv_value_0_0= RULE_DOUBLE ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3266:1: ( (lv_value_0_0= RULE_DOUBLE ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3267:1: (lv_value_0_0= RULE_DOUBLE )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3267:1: (lv_value_0_0= RULE_DOUBLE )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3268:3: lv_value_0_0= RULE_DOUBLE
            {
            lv_value_0_0=(Token)match(input,RULE_DOUBLE,FOLLOW_RULE_DOUBLE_in_ruleDoubleLiteral7922); 

            			newLeafNode(lv_value_0_0, grammarAccess.getDoubleLiteralAccess().getValueDOUBLETerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getDoubleLiteralRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"DOUBLE");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDoubleLiteral"


    // $ANTLR start "entryRuleLongLiteral"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3292:1: entryRuleLongLiteral returns [EObject current=null] : iv_ruleLongLiteral= ruleLongLiteral EOF ;
    public final EObject entryRuleLongLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLongLiteral = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3293:2: (iv_ruleLongLiteral= ruleLongLiteral EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3294:2: iv_ruleLongLiteral= ruleLongLiteral EOF
            {
             newCompositeNode(grammarAccess.getLongLiteralRule()); 
            pushFollow(FOLLOW_ruleLongLiteral_in_entryRuleLongLiteral7962);
            iv_ruleLongLiteral=ruleLongLiteral();

            state._fsp--;

             current =iv_ruleLongLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLongLiteral7972); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLongLiteral"


    // $ANTLR start "ruleLongLiteral"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3301:1: ruleLongLiteral returns [EObject current=null] : ( (lv_value_0_0= RULE_LONG ) ) ;
    public final EObject ruleLongLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3304:28: ( ( (lv_value_0_0= RULE_LONG ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3305:1: ( (lv_value_0_0= RULE_LONG ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3305:1: ( (lv_value_0_0= RULE_LONG ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3306:1: (lv_value_0_0= RULE_LONG )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3306:1: (lv_value_0_0= RULE_LONG )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3307:3: lv_value_0_0= RULE_LONG
            {
            lv_value_0_0=(Token)match(input,RULE_LONG,FOLLOW_RULE_LONG_in_ruleLongLiteral8013); 

            			newLeafNode(lv_value_0_0, grammarAccess.getLongLiteralAccess().getValueLONGTerminalRuleCall_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLongLiteralRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"LONG");
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLongLiteral"


    // $ANTLR start "entryRuleJsonArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3331:1: entryRuleJsonArray returns [EObject current=null] : iv_ruleJsonArray= ruleJsonArray EOF ;
    public final EObject entryRuleJsonArray() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJsonArray = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3332:2: (iv_ruleJsonArray= ruleJsonArray EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3333:2: iv_ruleJsonArray= ruleJsonArray EOF
            {
             newCompositeNode(grammarAccess.getJsonArrayRule()); 
            pushFollow(FOLLOW_ruleJsonArray_in_entryRuleJsonArray8053);
            iv_ruleJsonArray=ruleJsonArray();

            state._fsp--;

             current =iv_ruleJsonArray; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJsonArray8063); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJsonArray"


    // $ANTLR start "ruleJsonArray"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3340:1: ruleJsonArray returns [EObject current=null] : ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleValue ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleValue ) ) )* )? otherlv_5= ']' ) ;
    public final EObject ruleJsonArray() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_value_2_0 = null;

        EObject lv_value_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3343:28: ( ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleValue ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleValue ) ) )* )? otherlv_5= ']' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3344:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleValue ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleValue ) ) )* )? otherlv_5= ']' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3344:1: ( () otherlv_1= '[' ( ( (lv_value_2_0= ruleValue ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleValue ) ) )* )? otherlv_5= ']' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3344:2: () otherlv_1= '[' ( ( (lv_value_2_0= ruleValue ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleValue ) ) )* )? otherlv_5= ']'
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3344:2: ()
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3345:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getJsonArrayAccess().getJsonArrayAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,26,FOLLOW_26_in_ruleJsonArray8109); 

                	newLeafNode(otherlv_1, grammarAccess.getJsonArrayAccess().getLeftSquareBracketKeyword_1());
                
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3354:1: ( ( (lv_value_2_0= ruleValue ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleValue ) ) )* )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( ((LA26_0>=RULE_STRING && LA26_0<=RULE_LONG)||LA26_0==10||LA26_0==13||(LA26_0>=15 && LA26_0<=26)||(LA26_0>=28 && LA26_0<=34)) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3354:2: ( (lv_value_2_0= ruleValue ) ) (otherlv_3= ',' ( (lv_value_4_0= ruleValue ) ) )*
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3354:2: ( (lv_value_2_0= ruleValue ) )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3355:1: (lv_value_2_0= ruleValue )
                    {
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3355:1: (lv_value_2_0= ruleValue )
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3356:3: lv_value_2_0= ruleValue
                    {
                     
                    	        newCompositeNode(grammarAccess.getJsonArrayAccess().getValueValueParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleValue_in_ruleJsonArray8131);
                    lv_value_2_0=ruleValue();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getJsonArrayRule());
                    	        }
                           		add(
                           			current, 
                           			"value",
                            		lv_value_2_0, 
                            		"Value");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3372:2: (otherlv_3= ',' ( (lv_value_4_0= ruleValue ) ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==11) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3372:4: otherlv_3= ',' ( (lv_value_4_0= ruleValue ) )
                    	    {
                    	    otherlv_3=(Token)match(input,11,FOLLOW_11_in_ruleJsonArray8144); 

                    	        	newLeafNode(otherlv_3, grammarAccess.getJsonArrayAccess().getCommaKeyword_2_1_0());
                    	        
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3376:1: ( (lv_value_4_0= ruleValue ) )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3377:1: (lv_value_4_0= ruleValue )
                    	    {
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3377:1: (lv_value_4_0= ruleValue )
                    	    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3378:3: lv_value_4_0= ruleValue
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getJsonArrayAccess().getValueValueParserRuleCall_2_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleValue_in_ruleJsonArray8165);
                    	    lv_value_4_0=ruleValue();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getJsonArrayRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"value",
                    	            		lv_value_4_0, 
                    	            		"Value");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,27,FOLLOW_27_in_ruleJsonArray8181); 

                	newLeafNode(otherlv_5, grammarAccess.getJsonArrayAccess().getRightSquareBracketKeyword_3());
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJsonArray"


    // $ANTLR start "entryRuleBooleanLiteral"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3406:1: entryRuleBooleanLiteral returns [EObject current=null] : iv_ruleBooleanLiteral= ruleBooleanLiteral EOF ;
    public final EObject entryRuleBooleanLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBooleanLiteral = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3407:2: (iv_ruleBooleanLiteral= ruleBooleanLiteral EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3408:2: iv_ruleBooleanLiteral= ruleBooleanLiteral EOF
            {
             newCompositeNode(grammarAccess.getBooleanLiteralRule()); 
            pushFollow(FOLLOW_ruleBooleanLiteral_in_entryRuleBooleanLiteral8217);
            iv_ruleBooleanLiteral=ruleBooleanLiteral();

            state._fsp--;

             current =iv_ruleBooleanLiteral; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleBooleanLiteral8227); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBooleanLiteral"


    // $ANTLR start "ruleBooleanLiteral"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3415:1: ruleBooleanLiteral returns [EObject current=null] : ( (lv_value_0_0= ruleBOOL ) ) ;
    public final EObject ruleBooleanLiteral() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;


         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3418:28: ( ( (lv_value_0_0= ruleBOOL ) ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3419:1: ( (lv_value_0_0= ruleBOOL ) )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3419:1: ( (lv_value_0_0= ruleBOOL ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3420:1: (lv_value_0_0= ruleBOOL )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3420:1: (lv_value_0_0= ruleBOOL )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3421:3: lv_value_0_0= ruleBOOL
            {
             
            	        newCompositeNode(grammarAccess.getBooleanLiteralAccess().getValueBOOLParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_ruleBOOL_in_ruleBooleanLiteral8272);
            lv_value_0_0=ruleBOOL();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getBooleanLiteralRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_0_0, 
                    		"BOOL");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBooleanLiteral"


    // $ANTLR start "entryRuleBOOL"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3445:1: entryRuleBOOL returns [String current=null] : iv_ruleBOOL= ruleBOOL EOF ;
    public final String entryRuleBOOL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBOOL = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3446:2: (iv_ruleBOOL= ruleBOOL EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3447:2: iv_ruleBOOL= ruleBOOL EOF
            {
             newCompositeNode(grammarAccess.getBOOLRule()); 
            pushFollow(FOLLOW_ruleBOOL_in_entryRuleBOOL8308);
            iv_ruleBOOL=ruleBOOL();

            state._fsp--;

             current =iv_ruleBOOL.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleBOOL8319); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBOOL"


    // $ANTLR start "ruleBOOL"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3454:1: ruleBOOL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBOOL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3457:28: ( (kw= 'true' | kw= 'false' ) )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3458:1: (kw= 'true' | kw= 'false' )
            {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3458:1: (kw= 'true' | kw= 'false' )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==32) ) {
                alt27=1;
            }
            else if ( (LA27_0==33) ) {
                alt27=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3459:2: kw= 'true'
                    {
                    kw=(Token)match(input,32,FOLLOW_32_in_ruleBOOL8357); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getBOOLAccess().getTrueKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3466:2: kw= 'false'
                    {
                    kw=(Token)match(input,33,FOLLOW_33_in_ruleBOOL8376); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getBOOLAccess().getFalseKeyword_1()); 
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBOOL"


    // $ANTLR start "entryRuleNULL"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3479:1: entryRuleNULL returns [String current=null] : iv_ruleNULL= ruleNULL EOF ;
    public final String entryRuleNULL() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNULL = null;


        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3480:2: (iv_ruleNULL= ruleNULL EOF )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3481:2: iv_ruleNULL= ruleNULL EOF
            {
             newCompositeNode(grammarAccess.getNULLRule()); 
            pushFollow(FOLLOW_ruleNULL_in_entryRuleNULL8417);
            iv_ruleNULL=ruleNULL();

            state._fsp--;

             current =iv_ruleNULL.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNULL8428); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNULL"


    // $ANTLR start "ruleNULL"
    // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3488:1: ruleNULL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'null' ;
    public final AntlrDatatypeRuleToken ruleNULL() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3491:28: (kw= 'null' )
            // ../com.puppetlabs.geppetto.module.dsl/src-gen/com/puppetlabs/geppetto/module/dsl/parser/antlr/internal/InternalModule.g:3493:2: kw= 'null'
            {
            kw=(Token)match(input,34,FOLLOW_34_in_ruleNULL8465); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getNULLAccess().getNullKeyword()); 
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNULL"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleModel_in_entryRuleModel75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModel85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonMetadata_in_ruleModel131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonMetadata_in_entryRuleJsonMetadata165 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonMetadata175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_ruleJsonMetadata212 = new BitSet(new long[]{0x0000000003FFA010L});
    public static final BitSet FOLLOW_ruleMetadataPair_in_ruleJsonMetadata233 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_11_in_ruleJsonMetadata246 = new BitSet(new long[]{0x0000000003FFA010L});
    public static final BitSet FOLLOW_ruleMetadataPair_in_ruleJsonMetadata267 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_12_in_ruleJsonMetadata281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonObject_in_entryRuleJsonObject317 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonObject327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_ruleJsonObject373 = new BitSet(new long[]{0x00000000F3FFB010L});
    public static final BitSet FOLLOW_rulePair_in_ruleJsonObject395 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_11_in_ruleJsonObject408 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_rulePair_in_ruleJsonObject429 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_12_in_ruleJsonObject445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMetadataPair_in_entryRuleMetadataPair481 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMetadataPair491 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAuthorPair_in_ruleMetadataPair538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDependenciesPair_in_ruleMetadataPair565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleIssuesUrlPair_in_ruleMetadataPair592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLicensePair_in_ruleMetadataPair619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNamePair_in_ruleMetadataPair646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProjectPagePair_in_ruleMetadataPair673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSourcePair_in_ruleMetadataPair700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSummaryPair_in_ruleMetadataPair727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequirementsPair_in_ruleMetadataPair754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOperatingsystemSupportPair_in_ruleMetadataPair781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTagsPair_in_ruleMetadataPair808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVersionPair_in_ruleMetadataPair835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnrecognizedMetadataPair_in_ruleMetadataPair862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAuthorPair_in_entryRuleAuthorPair897 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAuthorPair907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_ruleAuthorPair950 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleAuthorPair975 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleStringLiteral_in_ruleAuthorPair996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDependenciesPair_in_entryRuleDependenciesPair1032 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDependenciesPair1042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_ruleDependenciesPair1085 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleDependenciesPair1110 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_ruleDependencyArray_in_ruleDependenciesPair1131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleIssuesUrlPair_in_entryRuleIssuesUrlPair1167 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleIssuesUrlPair1177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleIssuesUrlPair1220 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleIssuesUrlPair1245 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleStringLiteral_in_ruleIssuesUrlPair1266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLicensePair_in_entryRuleLicensePair1302 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLicensePair1312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleLicensePair1355 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleLicensePair1380 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleStringLiteral_in_ruleLicensePair1401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNamePair_in_entryRuleNamePair1437 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNamePair1447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleNamePair1490 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleNamePair1515 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleModuleName_in_ruleNamePair1536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOperatingsystemSupportPair_in_entryRuleOperatingsystemSupportPair1572 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOperatingsystemSupportPair1582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_ruleOperatingsystemSupportPair1625 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleOperatingsystemSupportPair1650 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_ruleOSArray_in_ruleOperatingsystemSupportPair1671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProjectPagePair_in_entryRuleProjectPagePair1707 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProjectPagePair1717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_ruleProjectPagePair1760 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleProjectPagePair1785 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleStringLiteral_in_ruleProjectPagePair1806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequirementsPair_in_entryRuleRequirementsPair1842 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRequirementsPair1852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleRequirementsPair1895 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleRequirementsPair1920 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_ruleRequirementArray_in_ruleRequirementsPair1941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSourcePair_in_entryRuleSourcePair1977 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSourcePair1987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleSourcePair2030 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleSourcePair2055 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleStringLiteral_in_ruleSourcePair2076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleSummaryPair_in_entryRuleSummaryPair2112 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleSummaryPair2122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleSummaryPair2165 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleSummaryPair2190 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleStringLiteral_in_ruleSummaryPair2211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTagsPair_in_entryRuleTagsPair2247 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTagsPair2257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleTagsPair2300 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleTagsPair2325 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_ruleTagArray_in_ruleTagsPair2346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVersionPair_in_entryRuleVersionPair2382 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVersionPair2392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_ruleVersionPair2435 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleVersionPair2460 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleVersion_in_ruleVersionPair2481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnrecognizedMetadataPair_in_entryRuleUnrecognizedMetadataPair2517 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnrecognizedMetadataPair2527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleUnrecognizedMetadataPair2569 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleUnrecognizedMetadataPair2586 = new BitSet(new long[]{0x00000007F7FFA470L});
    public static final BitSet FOLLOW_ruleValue_in_ruleUnrecognizedMetadataPair2607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStringArray_in_entryRuleStringArray2643 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStringArray2653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleStringArray2699 = new BitSet(new long[]{0x00000000FBFFA010L});
    public static final BitSet FOLLOW_ruleStringLiteral_in_ruleStringArray2721 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_11_in_ruleStringArray2734 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleStringLiteral_in_ruleStringArray2755 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_27_in_ruleStringArray2771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDependencyArray_in_entryRuleDependencyArray2807 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDependencyArray2817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleDependencyArray2863 = new BitSet(new long[]{0x0000000008000400L});
    public static final BitSet FOLLOW_ruleDependencyObject_in_ruleDependencyArray2885 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_11_in_ruleDependencyArray2898 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_ruleDependencyObject_in_ruleDependencyArray2919 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_27_in_ruleDependencyArray2935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDependencyObject_in_entryRuleDependencyObject2971 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDependencyObject2981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonDependency_in_ruleDependencyObject3027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonDependency_in_entryRuleJsonDependency3061 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonDependency3071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_ruleJsonDependency3108 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleDependencyPair_in_ruleJsonDependency3129 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_11_in_ruleJsonDependency3142 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleDependencyPair_in_ruleJsonDependency3163 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_12_in_ruleJsonDependency3177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDependencyPair_in_entryRuleDependencyPair3213 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDependencyPair3223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMetadataRefPair_in_ruleDependencyPair3270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVRPair_in_ruleDependencyPair3297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnrecognizedRequirementPair_in_ruleDependencyPair3324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMetadataRefPair_in_entryRuleMetadataRefPair3359 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMetadataRefPair3369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleMetadataRefPair3412 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleMetadataRefPair3437 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleMetadataRefPair3457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequirementArray_in_entryRuleRequirementArray3493 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRequirementArray3503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleRequirementArray3549 = new BitSet(new long[]{0x0000000008000400L});
    public static final BitSet FOLLOW_ruleRequirementObject_in_ruleRequirementArray3571 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_11_in_ruleRequirementArray3584 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_ruleRequirementObject_in_ruleRequirementArray3605 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_27_in_ruleRequirementArray3621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequirementObject_in_entryRuleRequirementObject3657 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRequirementObject3667 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonRequirement_in_ruleRequirementObject3713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonRequirement_in_entryRuleJsonRequirement3747 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonRequirement3757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_ruleJsonRequirement3794 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleRequirementPair_in_ruleJsonRequirement3815 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_11_in_ruleJsonRequirement3828 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleRequirementPair_in_ruleJsonRequirement3849 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_12_in_ruleJsonRequirement3863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequirementPair_in_entryRuleRequirementPair3899 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRequirementPair3909 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleRequirementPair3953 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleRequirementPair3978 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleRequirementName_in_ruleRequirementPair3999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVRPair_in_ruleRequirementPair4028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnrecognizedRequirementPair_in_ruleRequirementPair4055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequirementName_in_entryRuleRequirementName4090 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRequirementName4100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequirementNameValue_in_ruleRequirementName4146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRequirementNameValue_in_entryRuleRequirementNameValue4180 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRequirementNameValue4190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleRequirementNameValue4231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVRPair_in_entryRuleVRPair4271 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVRPair4281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_ruleVRPair4324 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleVRPair4349 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleVersionRange_in_ruleVRPair4370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnrecognizedRequirementPair_in_entryRuleUnrecognizedRequirementPair4406 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnrecognizedRequirementPair4416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQ_ReqUnknownKey_in_ruleUnrecognizedRequirementPair4462 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleUnrecognizedRequirementPair4474 = new BitSet(new long[]{0x00000007F7FFA470L});
    public static final BitSet FOLLOW_ruleValue_in_ruleUnrecognizedRequirementPair4495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVersionRange_in_entryRuleVersionRange4531 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVersionRange4541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonVersionRange_in_ruleVersionRange4587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonVersionRange_in_entryRuleJsonVersionRange4621 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonVersionRange4631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleJsonVersionRange4672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleModuleName_in_entryRuleModuleName4712 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleModuleName4722 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonModuleName_in_ruleModuleName4768 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonModuleName_in_entryRuleJsonModuleName4802 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonModuleName4812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleJsonModuleName4853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTagArray_in_entryRuleTagArray4893 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTagArray4903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleTagArray4949 = new BitSet(new long[]{0x00000000FBFFA010L});
    public static final BitSet FOLLOW_ruleTag_in_ruleTagArray4971 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_11_in_ruleTagArray4984 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleTag_in_ruleTagArray5005 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_27_in_ruleTagArray5021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTag_in_entryRuleTag5057 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTag5067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonTag_in_ruleTag5113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonTag_in_entryRuleJsonTag5147 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonTag5157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQ_StringOrKey_in_ruleJsonTag5202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVersion_in_entryRuleVersion5237 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVersion5247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonVersion_in_ruleVersion5293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonVersion_in_entryRuleJsonVersion5327 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonVersion5337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleJsonVersion5378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOSArray_in_entryRuleOSArray5418 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOSArray5428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleOSArray5474 = new BitSet(new long[]{0x0000000008000400L});
    public static final BitSet FOLLOW_ruleOSObject_in_ruleOSArray5496 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_11_in_ruleOSArray5509 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_ruleOSObject_in_ruleOSArray5530 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_27_in_ruleOSArray5546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOSObject_in_entryRuleOSObject5582 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOSObject5592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonOS_in_ruleOSObject5638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonOS_in_entryRuleJsonOS5672 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonOS5682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_ruleJsonOS5719 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleOSPair_in_ruleJsonOS5740 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_11_in_ruleJsonOS5753 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleOSPair_in_ruleJsonOS5774 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_12_in_ruleJsonOS5788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOSPair_in_entryRuleOSPair5824 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOSPair5834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_ruleOSPair5878 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleOSPair5903 = new BitSet(new long[]{0x00000000F3FFA010L});
    public static final BitSet FOLLOW_ruleStringLiteral_in_ruleOSPair5924 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_ruleOSPair5950 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleOSPair5975 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_ruleStringArray_in_ruleOSPair5996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnrecognizedOSPair_in_ruleOSPair6025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleUnrecognizedOSPair_in_entryRuleUnrecognizedOSPair6060 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleUnrecognizedOSPair6070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQ_OsUnknownKey_in_ruleUnrecognizedOSPair6116 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleUnrecognizedOSPair6128 = new BitSet(new long[]{0x00000007F7FFA470L});
    public static final BitSet FOLLOW_ruleValue_in_ruleUnrecognizedOSPair6149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulePair_in_entryRulePair6185 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulePair6195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQ_StringOrKey_in_rulePair6241 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_rulePair6253 = new BitSet(new long[]{0x00000007F7FFA470L});
    public static final BitSet FOLLOW_ruleValue_in_rulePair6274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValue_in_entryRuleValue6310 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleValue6320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBooleanLiteral_in_ruleValue6367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNullValue_in_ruleValue6394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStringLiteral_in_ruleValue6421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDoubleLiteral_in_ruleValue6448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLongLiteral_in_ruleValue6475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonObject_in_ruleValue6502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonArray_in_ruleValue6529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleStringLiteral_in_entryRuleStringLiteral6564 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleStringLiteral6574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQ_StringOrKey_in_ruleStringLiteral6619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQ_StringOrKey_in_entryRuleQ_StringOrKey6655 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQ_StringOrKey6666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_ruleQ_StringOrKey6704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_ruleQ_StringOrKey6723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleQ_StringOrKey6742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleQ_StringOrKey6761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleQ_StringOrKey6780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_ruleQ_StringOrKey6799 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_ruleQ_StringOrKey6818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_ruleQ_StringOrKey6837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_ruleQ_StringOrKey6856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_ruleQ_StringOrKey6875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleQ_StringOrKey6894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleQ_StringOrKey6913 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleQ_StringOrKey6932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleQ_StringOrKey6951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_ruleQ_StringOrKey6970 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_ruleQ_StringOrKey6989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleQ_StringOrKey7010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQ_ReqUnknownKey_in_entryRuleQ_ReqUnknownKey7056 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQ_ReqUnknownKey7067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_ruleQ_ReqUnknownKey7105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_ruleQ_ReqUnknownKey7124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleQ_ReqUnknownKey7143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleQ_ReqUnknownKey7162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_ruleQ_ReqUnknownKey7181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_ruleQ_ReqUnknownKey7200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_ruleQ_ReqUnknownKey7219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_ruleQ_ReqUnknownKey7238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_ruleQ_ReqUnknownKey7257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleQ_ReqUnknownKey7276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleQ_ReqUnknownKey7295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleQ_ReqUnknownKey7314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleQ_ReqUnknownKey7333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_ruleQ_ReqUnknownKey7352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleQ_ReqUnknownKey7373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQ_OsUnknownKey_in_entryRuleQ_OsUnknownKey7419 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQ_OsUnknownKey7430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_ruleQ_OsUnknownKey7468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_ruleQ_OsUnknownKey7487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleQ_OsUnknownKey7506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleQ_OsUnknownKey7525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleQ_OsUnknownKey7544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_ruleQ_OsUnknownKey7563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_ruleQ_OsUnknownKey7582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_ruleQ_OsUnknownKey7601 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleQ_OsUnknownKey7620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleQ_OsUnknownKey7639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleQ_OsUnknownKey7658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleQ_OsUnknownKey7677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_ruleQ_OsUnknownKey7696 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_ruleQ_OsUnknownKey7715 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleQ_OsUnknownKey7736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNullValue_in_entryRuleNullValue7781 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNullValue7791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNULL_in_ruleNullValue7836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDoubleLiteral_in_entryRuleDoubleLiteral7871 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDoubleLiteral7881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_DOUBLE_in_ruleDoubleLiteral7922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLongLiteral_in_entryRuleLongLiteral7962 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLongLiteral7972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_LONG_in_ruleLongLiteral8013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJsonArray_in_entryRuleJsonArray8053 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJsonArray8063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleJsonArray8109 = new BitSet(new long[]{0x00000007FFFFA470L});
    public static final BitSet FOLLOW_ruleValue_in_ruleJsonArray8131 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_11_in_ruleJsonArray8144 = new BitSet(new long[]{0x00000007F7FFA470L});
    public static final BitSet FOLLOW_ruleValue_in_ruleJsonArray8165 = new BitSet(new long[]{0x0000000008000800L});
    public static final BitSet FOLLOW_27_in_ruleJsonArray8181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBooleanLiteral_in_entryRuleBooleanLiteral8217 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBooleanLiteral8227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBOOL_in_ruleBooleanLiteral8272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleBOOL_in_entryRuleBOOL8308 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleBOOL8319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleBOOL8357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_ruleBOOL8376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNULL_in_entryRuleNULL8417 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNULL8428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_ruleNULL8465 = new BitSet(new long[]{0x0000000000000002L});

}